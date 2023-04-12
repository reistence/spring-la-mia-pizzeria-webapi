const form = document.getElementById("form");
const BE_URL = "http://localhost:8080/api/v1/pizzas";

const postBook = async (jsonData) => {
  const fetchOptions = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: jsonData,
  };
  const response = await fetch(BE_URL, fetchOptions);
  return response;
};

const saveBook = async (event) => {
  event.preventDefault();
  const formData = new FormData(event.target);
  const formDataObj = Object.fromEntries(formData.entries());
  const formDataJson = JSON.stringify(formDataObj);
  console.log(formDataJson);
  const response = await postBook(formDataJson);
  const responseBody = await response.json();
  if (response.ok) {
    loadData();
    event.target.reset();
  } else {
    console.log("ERROR");
    console.log(response.status);
    console.log(responseBody);
  }
};

form.addEventListener("submit", saveBook);
