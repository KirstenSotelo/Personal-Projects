document.addEventListener('DOMContentLoaded', function() {
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
  
    // Contact form submission
    const contactForm = document.getElementById('contact-form');
    contactForm.addEventListener('submit', function(e) {
      e.preventDefault();
      
      const name = document.getElementById('name').value;
      const email = document.getElementById('email').value;
      const message = document.getElementById('message').value;
  
      // Here you would typically send this data to a server
      // For this example, we'll just log it to the console
      console.log('Form submitted:', { name, email, message });
  
      // Show a success message
      alert('Thank you for your message! I\'ll get back to you soon.');
  
      // Clear the form
      contactForm.reset();
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
  });
  
  