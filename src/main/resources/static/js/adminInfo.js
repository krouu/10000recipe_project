
        function deleteUser(userId) {
            console.log("Deleting user with ID: " + userId); // 로그 추가
            if (confirm("정말로 이 사용자를 삭제하시겠습니까?")) {
                $.ajax({
                    url: '/api/users',
                    method: 'DELETE',
                    data: { userId: userId },
                    success: function(response) {
                        if (response === "success") {
                            alert("사용자가 삭제되었습니다.");
                            location.reload();
                        } else {
                            alert("사용자 삭제에 실패했습니다.");
                        }
                    },
                    error: function(error) {
                        console.error('Error deleting user:', error);
                        alert("사용자 삭제에 실패했습니다.");
                    }
                });
            }
        }

        $(document).ready(function() {
            $.ajax({
                url: '/api/users',
                method: 'GET',
                success: function(users) {
                    let tableBody = $('#memberTableBody');
                    users.forEach(function(user) {
                        let row = `
                            <tr>
                                <td>${user.username}</td>
                                <td>${user.userID}</td>
                                <td>${user.email}</td>
                                <td>${user.phoneNumber}</td>
                                <td>
                                    <button class="delete" onclick="deleteUser('${user.userID}')">삭제</button>
                                </td>
                            </tr>
                        `;
                        tableBody.append(row);
                    });
                },
                error: function(error) {
                    console.error('Error fetching users:', error);
                }
            });
        });