document.addEventListener('DOMContentLoaded', function() {
  const loginForm = document.getElementById('loginForm');
  const usernameInput = document.getElementById('username');
  const passwordInput = document.getElementById('password');
  const submitButton = loginForm.querySelector('button[type="submit"]');
  const spinner = submitButton.querySelector('.spinner-border');
  const toast = new bootstrap.Toast(document.querySelector('.toast'));
  const toastBody = document.querySelector('.toast-body');

  // Toggle password visibility
  document.querySelector('.password-toggle').addEventListener('click', function() {
    const icon = this.querySelector('i');
    if (passwordInput.type === 'password') {
      passwordInput.type = 'text';
      icon.classList.replace('bi-eye-fill', 'bi-eye-slash-fill');
    } else {
      passwordInput.type = 'password';
      icon.classList.replace('bi-eye-slash-fill','bi-eye-fill');
    }
  });

  // // Real-time validation
  // [usernameInput, passwordInput].forEach(input => {
  //   input.addEventListener('input', function() {
  //     validateInput(this);
  //     toggleSubmitButton();
  //   });
  // });

  // function validateInput(input) {
  //   if (input.value.trim() === '') {
  //     input.classList.add('is-invalid');
  //     return false;
  //   } else {
  //     input.classList.remove('is-invalid');
  //     return true;
  //   }
  // }

  // function toggleSubmitButton() {
  //   const isValid = validateInput(usernameInput) && validateInput(passwordInput);
  //   submitButton.disabled = !isValid;
  // }

  // Form submission
  // loginForm.addEventListener('submit', function(e) {
  //   e.preventDefault();
  //
  //   if (!loginForm.checkValidity()) {
  //     e.stopPropagation();
  //     loginForm.classList.add('was-validated');
  //     return;
  //   }

    // // Show loading state
    // submitButton.disabled = true;
    // spinner.style.display = 'inline-block';

    // // Simulate API call
    // setTimeout(() => {
    //   // Reset loading state
    //   submitButton.disabled = false;
    //   spinner.style.display = 'none';
    //
    //   // Show success message
    //   toastBody.textContent = 'Connexion réussie ! Redirection en cours...';
    //   toast.show();
    //
    //   // Simulate redirect
    //   setTimeout(() => {
    //     window.location.href = '/dashboard';
    //   }, 2000);
    // }, 1500);


  // });

  // // Initialize tooltips
  // const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
  // tooltipTriggerList.map(function (tooltipTriggerEl) {
  //   return new bootstrap.Tooltip(tooltipTriggerEl);
  // });
});
