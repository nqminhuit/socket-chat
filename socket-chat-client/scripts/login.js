const btnLogin = document.querySelector("#btnLogin");

btnLogin.addEventListener("click", function () {
  const loginForm = document.querySelector("#loginForm");
  loginForm.remove();
  const chatbox = document.querySelector("#chatbox");
  chatbox.style.display = "block";
});
