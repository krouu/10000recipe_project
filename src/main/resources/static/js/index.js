//$(document).ready(function() {
//    function loadRecipes() {
//        $.ajax({
//            url: '/api/recipes',
//            method: 'GET',
//            success: function(data) {
//                let recipesContainer = $('.recipe');
//                recipesContainer.empty();
//
//                data.forEach(function(recipe) {
//                    let recipeElement = `
//                        <div>
//                            <a href="/recipe/${recipe.recipeID}">
//                                <img src="${recipe.imgefile}" alt="Recipe Image" class="container_img">
//                                <h3>${recipe.recipeTitle}</h3>
//                            </a>
//                        </div>
//                    `;
//                    recipesContainer.append(recipeElement);
//                });
//            },
//            error: function(error) {
//                console.error('Error fetching recipes:', error);
//            }
//        });
//    }
//
//    $('form').on('submit', function(event) {
//        event.preventDefault();
//
//        let formData = new FormData(this);
//
//        $.ajax({
//            url: '/writing/save',
//            method: 'POST',
//            data: formData,
//            processData: false,
//            contentType: false,
//            success: function(response) {
//                alert(response);
//                loadRecipes();
//            },
//            error: function(error) {
//                alert("게시글 저장 중 오류가 발생했습니다.");
//                console.error('Error saving recipe:', error);
//            }
//        });
//    });
//
//    loadRecipes();
//});
