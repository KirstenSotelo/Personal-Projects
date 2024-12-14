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

    // Animate timeline items on scroll
    const timelineItems = document.querySelectorAll('.timeline-item');
    const animateTimeline = () => {
        timelineItems.forEach(item => {
            const itemTop = item.getBoundingClientRect().top;
            const itemBottom = item.getBoundingClientRect().bottom;
            if (itemTop < window.innerHeight && itemBottom > 0) {
                item.classList.add('show');
            }
        });
    };

    window.addEventListener('scroll', animateTimeline);
    animateTimeline(); // Initial check on page load

    // Adjust timeline item positions based on date
    const adjustTimelineItemPositions = () => {
        const timelineStart = 2015;
        const timelineEnd = 2023;
        const timelineDuration = timelineEnd - timelineStart;

        timelineItems.forEach(item => {
            const startYear = parseInt(item.style.getPropertyValue('--start-year'));
            const endYear = parseInt(item.style.getPropertyValue('--end-year'));
            
            const startPosition = ((startYear - timelineStart) / timelineDuration) * 100;
            const endPosition = ((endYear - timelineStart) / timelineDuration) * 100;
            
            item.style.left = `${startPosition}%`;
            item.style.width = `${endPosition - startPosition}%`;
        });
    };

    adjustTimelineItemPositions();
    window.addEventListener('resize', adjustTimelineItemPositions);
});

