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

    // Animate timeline entries on scroll
    const timelineEntries = document.querySelectorAll('.timeline-entry');
    const animateTimeline = () => {
        timelineEntries.forEach(entry => {
            const entryTop = entry.getBoundingClientRect().top;
            const entryBottom = entry.getBoundingClientRect().bottom;
            if (entryTop < window.innerHeight && entryBottom > 0) {
                entry.classList.add('show');
            }
        });
    };

    window.addEventListener('scroll', animateTimeline);
    animateTimeline(); // Initial check on page load

    // Position timeline entries
    const positionTimelineEntries = () => {
        const timelineStart = 2015;
        const timelineEnd = 2023;
        const timelineDuration = timelineEnd - timelineStart;
        const timelineEntriesContainer = document.querySelector('.timeline-entries');
        let lastEndPosition = 0;

        timelineEntries.forEach((entry, index) => {
            const startYear = parseInt(entry.style.getPropertyValue('--start-year'));
            const endYear = parseInt(entry.style.getPropertyValue('--end-year'));
            
            const startPosition = ((startYear - timelineStart) / timelineDuration) * 100;
            const endPosition = ((endYear - timelineStart) / timelineDuration) * 100;
            const width = endPosition - startPosition;

            entry.style.left = `${startPosition}%`;
            entry.style.width = `${width}%`;

            if (index > 0 && startPosition < lastEndPosition) {
                entry.style.top = `${parseInt(timelineEntries[index - 1].style.top || '0') + timelineEntries[index - 1].offsetHeight + 20}px`;
            } else {
                entry.style.top = '0px';
            }

            lastEndPosition = endPosition;
        });

        timelineEntriesContainer.style.height = `${timelineEntries[timelineEntries.length - 1].offsetTop + timelineEntries[timelineEntries.length - 1].offsetHeight}px`;
    };

    const positionTimelineDates = () => {
        const timelineStart = 2015;
        const timelineEnd = 2023;
        const timelineDuration = timelineEnd - timelineStart;
        const timelineDates = document.querySelectorAll('.timeline-date');
        const timelineWidth = document.querySelector('.timeline-line').offsetWidth;

        timelineDates.forEach((date, index) => {
            const year = timelineStart + index;
            const position = ((year - timelineStart) / timelineDuration) * timelineWidth;
            date.style.left = `${position}px`;
            date.textContent = year;
        });
    };

    positionTimelineEntries();
    positionTimelineDates();
    window.addEventListener('resize', () => {
        positionTimelineEntries();
        positionTimelineDates();
    });
});

