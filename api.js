async function loadProducts(){
    try{
      const repsonse = await fetch("http://localhost:8080/products/getProducts");
      const products  =  await repsonse.json()

      let clothinglist = document.getElementById("clothing-products");
      let Fitnesslist = document.getElementById("Fitness-products");
      let Homelist = document.getElementById("home-products");
       let bookslist = document.getElementById("books-products");

      clothinglist.innerHTML = "";
      Fitnesslist.innerHTML= "";
      Homelist.innerHTML = "";
      bookslist.innerHTML = "";

      products.forEach((product) => {
            let productcard = `
            <div class = "col-lg-4 col-md-6">
              <div class = "card h-100" > 
                         <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}">
                         <div class = "card-body d-flex flex-column">
                         <h5 class ="card-title">${product.name}</h5>
                         <p class="card-text">${product.description}</p>
                         <p class="price"><strong>₹${product.price}</strong></p>
                         <button class="btn btn-primary mt-auto"
                         onclick="addToCart(${product.id},'${product.name}','${product.price}','${product.imageUrl}')"
                         >
                         Add to Cart
                         </button>
                 </div>
            </div>
            `;

            if(product.category ===  "Clothing" ){
                 clothinglist.innerHTML +=  productcard
            }
            else if(product.category ===  "Fitness"){
                  clothinglist.innerHTML +=  productcard
            }
            else if(product.category ===  "Home & Kitchen"){
                 clothinglist.innerHTML +=  productcard
            }
            else if(product.category ===  "Books"){
                   clothinglist.innerHTML +=  productcard
            }
          
      });

    }
    catch(error){
           console.log(error);
    }
}