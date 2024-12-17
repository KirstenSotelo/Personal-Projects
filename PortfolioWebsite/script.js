// Contact form submission
import { WEB3FORMS_ACCESS_KEY } from './config.js';

document.addEventListener('DOMContentLoaded', function() {
  // Theme switching functionality
  const themeButtons = document.querySelectorAll('.theme-button');
  const setActiveTheme = (theme) => {
    document.body.className = theme === 'light' ? '' : `${theme}-theme`;
    themeButtons.forEach(btn => {
      btn.classList.toggle('active', btn.dataset.theme === theme);
      btn.setAttribute('aria-pressed', btn.dataset.theme === theme);
    });
    // Re-run feather.replace() to update icons with new colors
    feather.replace();
    // Save the active theme to localStorage
    localStorage.setItem('activeTheme', theme);
  };

  themeButtons.forEach(button => {
    button.addEventListener('click', () => {
      const theme = button.dataset.theme;
      setActiveTheme(theme);
    });
  });

  // Set initial theme from localStorage or default to 'light'
  const savedTheme = localStorage.getItem('activeTheme') || 'light';
  setActiveTheme(savedTheme);

  // Smooth scrolling for navigation links
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      e.preventDefault();

      document.querySelector(this.getAttribute('href')).scrollIntoView({
        behavior: 'smooth'
      });
    });
  });

  // Mobile menu toggle
  const menuButton = document.createElement('button');
  menuButton.textContent = 'Menu';
  menuButton.classList.add('menu-toggle');
  document.querySelector('nav').appendChild(menuButton);

  const navList = document.querySelector('nav ul');
  menuButton.addEventListener('click', () => {
    navList.classList.toggle('show');
  });

  const contactForm = document.getElementById('contact-form');
  contactForm.addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const formData = new FormData(this);
    formData.append('access_key', WEB3FORMS_ACCESS_KEY);

    try {
      const response = await fetch('https://api.web3forms.com/submit', {
        method: 'POST',
        body: formData
      });

      const result = await response.json();
      if (response.status === 200) {
        alert('Thank you for your message! I\'ll get back to you soon.');
        contactForm.reset();
      } else {
        alert('Oops! Something went wrong. Please try again later.');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('Oops! Something went wrong. Please try again later.');
    }
  });

  // Load Feather icons
  feather.replace();

  // Position timeline entries
  const positionTimelineEntries = () => {
    const timelineEntries = document.querySelectorAll('.timeline-entry');
    const timelineEntriesContainer = document.querySelector('.timeline-entries');
    const timelineLine = document.querySelector('.timeline-line');
    const experienceSection = document.querySelector('.experience');
    
    // Find the earliest and latest dates
    let earliestDate = new Date('9999-12-31');
    let latestDate = new Date('0000-01-01');
    
    timelineEntries.forEach(entry => {
      const startDate = new Date(entry.dataset.start);
      const endDate = new Date(entry.dataset.end);
      
      if (startDate < earliestDate) earliestDate = startDate;
      if (endDate > latestDate) latestDate = endDate;
    });
    
    // Add padding to the start and end dates (6 months before and after)
    earliestDate = new Date(earliestDate.getFullYear(), earliestDate.getMonth() - 6, 1);
    latestDate = new Date(latestDate.getFullYear(), latestDate.getMonth() + 7, 0); // End of the month, 6 months after

    const timelineDuration = latestDate.getTime() - earliestDate.getTime();

    if (window.innerWidth > 768) {
      let rows = []; // Array to keep track of the end of each row
      let maxRowHeight = 0; // Track the maximum height of any entry

      timelineEntries.forEach((entry, index) => {
        const startDate = new Date(entry.dataset.start);
        const endDate = new Date(entry.dataset.end);
        
        const startPosition = ((startDate.getTime() - earliestDate.getTime()) / timelineDuration) * 100;
        const endPosition = ((endDate.getTime() - earliestDate.getTime()) / timelineDuration) * 100;
        const width = endPosition - startPosition;

        entry.style.left = `${startPosition}%`;
        entry.style.width = `${width}%`;

        // Find the appropriate row for this entry
        let rowIndex = rows.findIndex(row => row <= startPosition);
        if (rowIndex === -1) {
          // If no suitable row found, create a new one
          rowIndex = rows.length;
          rows.push(0);
        }

        // Set the top position based on the row
        const topPosition = rowIndex * (maxRowHeight + 20);
        entry.style.top = `${topPosition}px`;

        // Update the end position of this row
        rows[rowIndex] = endPosition;

        // Update maxRowHeight if this entry is taller
        const entryHeight = entry.offsetHeight;
        if (entryHeight > maxRowHeight) {
          maxRowHeight = entryHeight;
          // Adjust all previous entries to the new row height
          timelineEntries.forEach((prevEntry, prevIndex) => {
            if (prevIndex < index) {
              const prevRowIndex = Math.floor(parseInt(prevEntry.style.top) / (maxRowHeight + 20));
              prevEntry.style.top = `${prevRowIndex * (maxRowHeight + 20)}px`;
            }
          });
        }
      });

      const totalHeight = rows.length * (maxRowHeight + 20) - 20; // Subtract the last margin
      timelineEntriesContainer.style.height = `${totalHeight}px`;
      experienceSection.style.minHeight = `${totalHeight + 150}px`; // Add extra space for padding and headers
    } else {
      timelineEntries.forEach(entry => {
        entry.style.left = '';
        entry.style.width = '';
        entry.style.top = '';
      });
      timelineEntriesContainer.style.height = '';
      experienceSection.style.minHeight = '';
    }

    // Update timeline dates
    timelineLine.innerHTML = '';
    const years = new Set();
    for (let year = earliestDate.getFullYear(); year <= latestDate.getFullYear(); year++) {
      years.add(year);
    }

    years.forEach(year => {
      const dateElement = document.createElement('div');
      dateElement.classList.add('timeline-date');
      dateElement.textContent = year;
      const position = ((new Date(year, 0, 1).getTime() - earliestDate.getTime()) / timelineDuration) * 100;
      dateElement.style.left = `${position}%`;
      timelineLine.appendChild(dateElement);
    });

    // Adjust the position of the first and last date labels
    const dateLabels = timelineLine.querySelectorAll('.timeline-date');
    if (dateLabels.length > 0) {
      dateLabels[0].style.left = '0%';
      dateLabels[dateLabels.length - 1].style.left = '100%';
      dateLabels[dateLabels.length - 1].style.transform = 'translateX(-100%)';
    }
  };

  window.addEventListener('resize', positionTimelineEntries);
  window.addEventListener('load', positionTimelineEntries);
  positionTimelineEntries();

  // Scroll-triggered animations
  const animatedElements = document.querySelectorAll('.fade-in, .slide-in-left, .slide-in-right, .scale-in');

  const animateOnScroll = (entries, observer) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('appear');
        observer.unobserve(entry.target);
      }
    });
  };

  const options = {
    root: null,
    rootMargin: '0px',
    threshold: 0.1
  };

  const observer = new IntersectionObserver(animateOnScroll, options);

  animatedElements.forEach(element => {
    observer.observe(element);
  });
});

