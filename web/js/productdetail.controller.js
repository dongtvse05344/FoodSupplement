var a = [1, 2, 3, 4]
for (let i = 0; i < 4; i++) {
    setTimeout(function () {
       console.log("position " + i +" has value is " + a[i] )
    }, 3000);
}