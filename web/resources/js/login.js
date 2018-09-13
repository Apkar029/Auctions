/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

console.log("hellow world!");

$(".signInInfo").focus(function () {
    console.log("Hello!");
    $(this).parent("#login").css("display", "block");

    $(this).blur(function () {
        $(this).parent("#login").css("display", "none");
    });
});

$(".signInInfo").attr("required", "");

console.log("goodbye world!");