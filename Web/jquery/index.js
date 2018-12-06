const row = 13;
const column = 13;
const cell = 4;
$(document).ready(function () {
  // 生成输入框
  for (let i = 0; i < row; i++) {
    const rows = $("<div class='row'></div>");
    for (let j = 0; j < column; j++) {
      const column = $("<div class='column'></div>");
      for (let m = 0; m < cell; m++) {
        column.append($(`<input name ='${i}_${j}_${m}' value = '-.-' />`));
      }
      rows.append(column);
    }
    $("#container").append(rows);
  }
  // 添加提交按钮
  $("#container").append($("<input type='submit' value='Submit'/>"));
  $("input").change(function (e) {
    const target = e.target;
    const name = target.name;
    const value = target.value;
    console.log(name, value);
  });

  //对提交的事件进行处理
  $("#container").submit(function (event) {
    // 生成一个二维数组
    let datas = [];
    for (let i = 0; i < row; i++) {
      const subData = [];
      for (let j = 0; j < column; j++) {
        const cellData = [];
        for (let m = 0; m < cell; m++) {
          cellData.push('-.-');
        }
        subData.push(cellData);
      }
      datas.push(subData);
    }
    // 填充二维数据
    let $inputs = $('#container :input');
    $inputs.each(function () {
      const name = this.name;
      if (name) {
        const idxs = name.split('_');
        const _r = parseInt(idxs[0]);
        const _c = parseInt(idxs[1]);
        const _t = parseInt(idxs[2]);
        datas[_r][_c][_t] = $(this).val();
      }
    });
    console.log(datas);
    $.ajax({
      type: 'POST',
      crossDomain: true,
      dataType: 'json',
      url: 'http://localhost:3005/submit',
      data: JSON.stringify(datas),
      success: function (result, status) {
        console.log(result);
        console.log(status);
      },
      error: function (xhr, status, error) {
        console.log(status);
        console.log(error);

      }
    });
    // 网络提交数据
    event.preventDefault();
  });
});

