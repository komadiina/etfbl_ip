let timerInterval;
let seconds = 0;

function init() {
  document.getElementById('startRentalBtn').addEventListener('click', startTimer);
  document.getElementById('stopRentalBtn').addEventListener('click', stopRental);
}

function startTimer(event) {
  event.preventDefault();
  document.getElementById('rentalForm').submit();
  timerInterval = setInterval(updateTimer, 1000);
  // console.log("started");

  // unhide the stopRentalDiv
  // document.getElementById('stopRentalDiv').style.display = 'block';
}

function updateTimer() {
  seconds++;
  // console.log(seconds);
  document.getElementById('timer').innerText = formatTime(seconds);
}

function formatTime(totalSeconds) {
  let hours = Math.floor(totalSeconds / 3600);
  let minutes = Math.floor((totalSeconds % 3600) / 60);
  let seconds = totalSeconds % 60;
  return `${pad(hours)}:${pad(minutes)}:${pad(seconds)}`;
}

function pad(num) {
  return num.toString().padStart(2, '0');
}

function stopRental(event) {
  event.preventDefault();
  clearInterval(timerInterval);
  document.getElementById('rentalAction').value = 'stop';
  document.getElementById('rentalForm').submit();
}
