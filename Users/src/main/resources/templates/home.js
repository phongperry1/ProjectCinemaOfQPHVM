let marker = document.querySelector('.marker');
let items = document.querySelectorAll('nav ul li');


function indicator(e){
  marker.style.left = e.offsetLeft + "px";
  marker.style.width = e.offsetWidth + "px";
}

items.forEach(link =>{
  link.addEventListener("click",(e)=>{
    indicator(e.target);
  })
})



//for menu scroll
let nav = document.querySelector('nav');
let ul = document.querySelector('nav ul');

window.addEventListener("scroll",()=>{
  if(window.pageYOffset >= 20){
    nav.classList.add('nav');
  }else{
    nav.classList.remove('nav');
  }
  
    if(window.pageYOffset >= 700){
    nav.classList.add('navBlack');
  }else{
    nav.classList.remove('navBlack');
  }
})



//menu
let menu = document.querySelector('#menu');
let menuBx = document.querySelector('#menu-box');
let a = true;

menu.addEventListener("click",()=>{
  
  if(a == true){
    menuBx.style.display = "block";
    menu.classList.replace("fa-bars","fa-remove");
    a = false;
  }else{
    menuBx.style.display = "none";
    menu.classList.replace("fa-remove","fa-bars");
    a = true;
  }
  
})



 $(".carousel").owlCarousel({
           margin: 20,
           loop: true,
           autoplay: true,
           autoplayTimeout: 5000,
           autoplayHoverPause: true,
           responsive: {
             0:{
               items:3,
               nav: true
             },
             600:{
               items:3,
               nav: true
             },
             1000:{
               items:3,
               nav: true
             }
           }
   });