let password = document.getElementById("pass");
let confirmPassword = document.getElementById("confirmpass");
let submiting = document.getElementById("submit");
let warning = document.getElementById("warn");
let success = document.getElementById("done");
let failed = document.getElementById("failed");


let backendURL = 'http://localhost:9999'


let uqid=document.getElementById("uqid");


submiting.addEventListener('click',onsubmiting);


function onsubmiting(){
    let passValue = password.value;
    let confirmPassValue = confirmPassword.value;

    if(passValue === confirmPassValue){
    	warning.style.display='none';
        let restReset = fetch(backendURL + '/identity-management/v1/users/account-management/'+uqid.textContent,{
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({'password': passValue})
        }).then(function(response){
        	if(response.status === 200){
        		success.style.display= 'block';
        		failed.style.display = 'none';
        		
        	}
        	else if (response.status === 410) {
        		success.style.display= 'none';
        		failed.style.display = 'block';
        		failed.textContent='Uh oh! The link expired';
			}
        	else{
        		success.style.display= 'none';
        		failed.style.display = 'block';
        		failed.textContent='Password reset failed';
        	}
        }).catch(error=>{
        	success.style.display= 'none';
    		failed.style.display = 'block';
    		failed.textContent='Password reset failed';

        });
    }
    else{
        warning.style.display='block';
        success.style.display= 'none';
        failed.style.display = 'none';
    }
}