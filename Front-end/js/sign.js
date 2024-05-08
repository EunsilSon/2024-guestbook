/**
 *  로그인 관련
 */

const form = document.getElementById('form');
let userName = document.getElementById('user_name');
let userPw = document.getElementById('user_pw');
let userTel = document.getElementById('user_tel');
let isRight;

function checkUsername(username) {
  if (username.length < 2) {
    swal('입력 값 불일치!',"아이디를 최소 2자 이상 입력하세요.",'warning')
    .then(function(){     
      removeName();
    })
    return false;
  } else {
    return true;
  }
}

function checkUserPw(userPw) {
  if (userPw.length < 8) {
    swal('입력 값 불일치!',"비밀번호를 최소 8자 이상 입력하세요.",'warning')
    .then(function(){      
      removePw();
    })
    return false;
  } else {
    return true;
  }
}

function checkTel(userTel) {
  if (userTel.length != 11) {
    swal('입력 값 불일치!',"연락처를 11자 입력하세요.",'warning')
    .then(function(){   
      removeTel();
    })
    return false;
  } else {
    return true;
  }
}


// 입력 값 지우기 
function removeName() {
  userName.value = "";
}

function removePw() {
  userPw.value = "";
}

function removeTel() {
  userTel.value = "";
}

// 로그인
function signIn() {
  isRight = checkUsername(userName.value) && checkUserPw(userPw.value);

  if (isRight) {
    axios({
      method: 'post', //통신 방식
      url: 'http://127.0.0.1:8000/sign_in',
      data: {
        "username": userName.value,
        "password": userPw.value
      }
    }, { withCredentials : true })
      .then((Response)=>{
        if (Response.data == "fail") {
          swal('로그인 실패!',"아이디 및 비밀번호가 올바르지 않습니다.",'error')
          .then(function(){
            removeName();
            removePw();            
          })
        } else {
          swal('로그인 성공!',userName.value+"님 로그인 되었습니다.",'success')
          .then(function(){
            localStorage.setItem('username', userName.value);
            form.submit();
            removeName();
            removePw();                  
          })
        }
  }).catch((Error)=>{
      console.log(Error);
  })
  }
}


// 회원가입
function signUp() {
  isRight = checkUsername(userName.value) && checkUserPw(userPw.value) && checkTel(userTel.value);

  if (isRight) {

    axios({
      method: 'post', //통신 방식
      url: 'http://127.0.0.1:8000/sign_up',
      data: {
        "username": userName.value,
        "password": userPw.value,
        "telephone": userTel.value
      }
    }, { withCredentials : true })
      .then((Response)=>{
        if (Response.data == "Existed Username") {
          swal("존재하는 아이디입니다.")
          .then(function(){
            removeName();                
          })  
        } else if (Response.data == "Existed telephone") {
          swal("존재하는 연락처입니다.")
          .then(function(){
            removeTel();                 
          })
        } else {
          swal("회원가입 성공!", "로그인 후 서비스를 이용하세요.", "success")
          .then(function(){
            form.submit();
            removeName();
            removePw();  
            removeTel();                 
          })
        }
  }).catch((Error)=>{
      console.log(Error);
  })
  }
}

// id 찾기 (completed)
function findId() {
  isRight = checkTel(userTel.value);

  if (isRight) {
    axios({
      method: 'get', //통신 방식
      url: 'http://127.0.0.1:8000/id',
      params: {
        "tel": userTel.value
      }
    }, { withCredentials : true })
      .then((Response)=>{
        if (Response.data == "fail") {
          swal('ID 찾기 실패!',"일치하는 사용자가 없습니다.",'info')
          .then(function(){
            removeTel();           
          })
        } else {
          swal('ID 찾기 성공!',"아이디는 ▶ " + Response.data + " ◀ 입니다.",'success')
          .then(function(){
            form.submit();
            removeName();             
          })
        }
  }).catch((Error)=>{
      console.log(Error);
  })
  }
}

// pw 찾기
function findPw() {
  isRight = checkUsername(userName.value) && checkTel(userTel.value);

  if (isRight) {

    axios({
      method: 'get', //통신 방식
      url: 'http://127.0.0.1:8000/pw',
      params: {
        "name": userName.value,
        "tel" : userTel.value
      }
    }, { withCredentials : true })
      .then((Response)=>{
        if (Response.data == "fail") {
          swal('PW 찾기 실패!',"일치하는 사용자가 없습니다.",'info')
          .then(function(){
            removeTel();           
          })
        } else {
          swal('PW 찾기 성공!',"비밀번호는 ▶ " + Response.data + " ◀ 입니다.",'success')
          .then(function(){
            form.submit();
            removeName();             
          })
        }
  }).catch((Error)=>{
      console.log(Error);
  })
  }
}