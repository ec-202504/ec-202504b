"use strict";
$(() => {
  $("#btn").on("click", () => {
    $.ajax({
      url: "https://zipcoda.net/api",
      type: "GET",
      dataType: "JSON",
      data: {
        zipcode: $("#zipcode").val(),
      },
      async: true,
    })
      .done((data) => {
        console.log(data);
        console.dir(JSON.stringify(JSON));
        if (Object.keys(data.items).length === 0) {
          alert("住所はありません");
        } else {
          $("#prefecture").val(data.items[0].components[0]);
          $("#municipalities").val(data.items[0].components[1]);
          $("#address").val(data.items[0].components[2]);
        }
      })
      .fail((XMLHttpRequest, testStatus, errorThrown) => {
        alert("正しい結果を得られませんでした");
        console.log("XMLHttpRequest :" + XMLHttpRequest.status);
        console.log("testStatus :" + testStatus);
        console.log("errorThrown :" + errorThrown.message);
      });
  });
});
