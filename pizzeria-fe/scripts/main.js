//Global
const BE_URL = "http://localhost:8080/api/v1/pizzas";
const contentEl = document.getElementById("content");
const searchForm = document.querySelector(".searchForm");
const searchInput = document.getElementById("q");

let q = "";
//API
const getAllPizzas = async () => {
  const response = await fetch(BE_URL + "?q=" + q);
  return response;
};

const delPizzaById = async (id) => {
  const response = await fetch(BE_URL + "/" + id, { method: "DELETE" });
  return response;
};

// DOM Elements
const createIngredientsList = (ingredients) => {
  let ingredientsEl =
    '<div class="d-flex align-items-center justify-content-center flex-wrap w-75" style="margin: 0 auto; gap: .5em; height:"min-content"">  ';
  ingredients.forEach((el) => {
    ingredientsEl += `<span class="mx-1 ingredient">${el.name}</span>`;
  });
  ingredientsEl += "</div>";
  return ingredientsEl;
};

const createDeleteBtn = (pizza) => {
  let btn = "";
  btn = `<button data-id="${pizza.id}" class="btn my-btn">
              Delete
          </button>`;
  return btn;
};

const pizzaEl = (item) => {
  return `<div class="col-6">
                <div class="my-card ">
                    <div class="my-card-body">
                        <h5 class="card-title">${item.name}</h5>
                            <span class="price">€ ${item.price}</span>
                           
                       
                        <p >${
                          item.description
                            ? item.description
                            : "Nessuna descrizione"
                        }</p>
                        
                        
                       
                    </div>
                    <div class="my-card-footer px-2 mb-1">
                        ${createIngredientsList(item.ingredients)}
                    </div>
                    <div class="my-3">${createDeleteBtn(item)}</div>
                    <img class="cover" src="${item.cover}" alt="" srcset="">
                    
                </div>
            </div>`;
};

const createPizzaList = (data) => {
  console.log(data);
  let list = `<div class="row gy-3">`;
  // add book items
  data.forEach((element) => {
    list += pizzaEl(element);
  });
  list += "</div>";
  return list;
};

//Fns
const loadData = async () => {
  // call api
  const response = await getAllPizzas();
  console.log(response);
  if (response.ok) {
    // get data
    const data = await response.json();
    // render html
    contentEl.innerHTML = createPizzaList(data);
    // add event listeners
    const deleteBtns = document.querySelectorAll("button[data-id]");
    // console.log(deleteBtns);
    for (let btn of deleteBtns) {
      btn.addEventListener("click", () => {
        delPizza(btn.dataset.id);
      });
    }
  } else {
    // handle error
    console.log("ERROR");
  }
};

const delPizza = async (id) => {
  console.log("delete pizza " + id);
  // call delete api
  const response = await delPizzaById(id);
  // parse response
  if (response.ok) {
    // reload book list
    loadData();
  } else {
    // handle error
    console.log("ERROR");
    console.log(response.status);
  }
};

searchForm.addEventListener("submit", (e) => {
  e.preventDefault();
  q = searchInput.value;
  //   console.log(q);
  loadData();
});

loadData();
