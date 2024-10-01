document.addEventListener('DOMContentLoaded', function() {
  // Sidebar Toggle Functionality
  const sidebarToggler = document.querySelector('.sidebar-toggler');
  const sidebar = document.querySelector('.sidebar');
  const mainContent = document.querySelector('.main-content');
  const header = document.querySelector('.header');

  sidebarToggler.addEventListener('click', function() {
    sidebar.classList.toggle('active');
  });

  // Responsive handling
  function handleResize() {
    if (window.innerWidth <= 768) {
      sidebar.classList.remove('active');
      mainContent.style.marginLeft = '0';
      header.style.left = '0';
    } else {
      sidebar.classList.add('active');
      mainContent.style.marginLeft = `${sidebar.offsetWidth}px`;
      header.style.left = `${sidebar.offsetWidth}px`;
    }
  }

  window.addEventListener('resize', handleResize);
  handleResize(); // Initial call

  // Initialize all tooltips
  const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
  tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
  });

  // Simulate loading data
  setTimeout(() => {
    document.querySelectorAll('.animate-fade-in').forEach(el => {
      el.style.opacity = '1';
      el.style.transform = 'translateY(0)';
    });
  }, 100);
});
