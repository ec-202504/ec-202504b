"use strict";
$(() => {
  // 初期表示時は商品の価格
  const price = $("#itemPrice").val();
  $("#subTotalPrice").text(price);

  // 数量が変更されるとその数だけ乗算される
  $("#quantity-select").on("change", () => {
    const quantity = $("#quantity-select").val();
    const subTotalPrice = quantity * price;
    $("#subTotalPrice").text(subTotalPrice);
  });
});
