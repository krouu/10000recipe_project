document.addEventListener('DOMContentLoaded', function() {
    const idCheckButton = document.getElementById('idCheck');
    const submitButton = document.getElementById('submitBtn');
    const idInput = document.getElementById('id');
    const resultElement = document.getElementById('userID_result');
    const phoneNumberInput = document.querySelector('input[name="phoneNumber"]');
    let isIDChecked = false;

    idCheckButton.addEventListener('click', function(event) {
        event.preventDefault();
        const userID = idInput.value;

        if (userID === '') {
            alert('아이디를 입력해주세요.');
            return;
        }

        fetch(`/checkUserID?userID=${userID}`)
            .then(response => response.text())
            .then(data => {
                if (data === 'exists') {
                    resultElement.textContent = '중복된 아이디';
                    resultElement.style.color = 'red';
                    isIDChecked = false;
                } else {
                    resultElement.textContent = '사용 가능한 아이디';
                    resultElement.style.color = 'green';
                    isIDChecked = true;
                }
            })
            .catch(error => console.error('Error:', error));
    });

    document.getElementById('signupForm').addEventListener('submit', function(event) {
        if (!isIDChecked || resultElement.textContent === '중복된 아이디') {
            alert('아이디 중복검사를 통과하지 못했습니다.');
            idInput.value = '';
            idInput.focus();
            resultElement.textContent = '';
            event.preventDefault(); // 폼 제출 방지
        }
    });

    phoneNumberInput.addEventListener("input", function(event) {
        let input = event.target.value;
        input = input.replace(/\D/g, ''); // 숫자가 아닌 모든 문자 제거
        if (input.length <= 3) {
            input = input;
        } else if (input.length <= 7) {
            input = input.replace(/(\d{3})(\d+)/, '$1-$2');
        } else if (input.length <= 11) {
            input = input.replace(/(\d{3})(\d{4})(\d+)/, '$1-$2-$3');
        } else {
            input = input.substring(0, 11).replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
        }
        event.target.value = input;
    });
});
