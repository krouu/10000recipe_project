function deleteRecipe(recipeID) {
    console.log("Deleting recipe with ID: " + recipeID); // 로그 추가
    if (confirm("정말로 이 레시피를 삭제하시겠습니까?")) {
        $.ajax({
            url: '/api/recipes/' + recipeID,
            method: 'DELETE',
            success: function(response) {
                alert("레시피가 삭제되었습니다.");
                location.reload();
            },
            error: function(error) {
                console.error('Error deleting recipe:', error);
                alert("레시피 삭제에 실패했습니다.");
            }
        });
    }
}

$(document).ready(function() {
    const userId = window.location.pathname.split('/').pop(); // URL에서 userId 추출

    $.ajax({
        url: `/api/user/${userId}/recipes`,
        method: 'GET',
        success: function(data) {
            let recipeList = $('#recipeList');
            $('#username').text(data.username);
            data.recipes.forEach(function(recipe) {
                let row = `
                    <tr class="recipe-row" data-recipe-id="${recipe.recipeID}">
                        <td>${recipe.recipeTitle}</td>
                        <td>${recipe.category}</td>
                        <td>${recipe.ingredients}</td>
                        <td>${recipe.gastronomy}</td>
                        <td><img src="${recipe.imgefile}" alt="레시피 이미지" class="recipe-image"></td>
                        <td><button class="delete" onclick="deleteRecipe(${recipe.recipeID})">삭제</button></td>
                    </tr>
                `;
                recipeList.append(row);
            });
        },
        error: function(error) {
            console.error('Error fetching user recipes:', error);
        }
    });
});
