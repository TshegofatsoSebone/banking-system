const AUTH_API = "http://localhost:8085/auth";
const ACCOUNT_API = "http://localhost:8085/api/accounts";

async function register(){

    const fullName = document.getElementById("fullName").value;
    const email = document.getElementById("email").value;
    const phoneNumber = document.getElementById("phoneNumber").value;
    const idNumber = document.getElementById("idNumber").value;
    const pin = document.getElementById("pin").value;

    const response = await fetch(`${AUTH_API}/register`,{

        method:"POST",

        headers:{
            "Content-Type":"application/json"
        },

        body:JSON.stringify({
            fullName,
            email,
            phoneNumber,
            idNumber,
            pin
        })
    });

    const message = await response.text();

    document.getElementById("message").innerText = message;
}

async function login(){

    const email = document.getElementById("loginEmail").value;
    const pin = document.getElementById("loginPin").value;

    const response = await fetch(`${AUTH_API}/login`,{

        method:"POST",

        headers:{
            "Content-Type":"application/json"
        },

        body:JSON.stringify({
            email,
            pin
        })
    });

    if(response.ok){

        window.location.href="dashboard.html";

    }else{

        document.getElementById("loginMessage").innerText="Invalid login";
    }
}

async function getAccount(){

    const id = document.getElementById("accountId").value;

    const response = await fetch(`${ACCOUNT_API}/${id}`);

    const data = await response.json();

    document.getElementById("balance").innerText="Balance: R"+data.balance;
}

async function deposit(){

    const id = document.getElementById("depositAccountId").value;
    const amount = document.getElementById("depositAmount").value;

    await fetch(`${ACCOUNT_API}/${id}/deposit?amount=${amount}`,{
        method:"POST"
    });

    alert("Deposit successful");
}

async function withdraw(){

    const id = document.getElementById("withdrawAccountId").value;
    const amount = document.getElementById("withdrawAmount").value;

    await fetch(`${ACCOUNT_API}/${id}/withdraw?amount=${amount}`,{
        method:"POST"
    });

    alert("Withdrawal successful");
}

async function transfer(){

    const from = document.getElementById("fromAccount").value;
    const to = document.getElementById("toAccount").value;
    const amount = document.getElementById("transferAmount").value;

    await fetch(`${ACCOUNT_API}/transfer?from=${from}&to=${to}&amount=${amount}`,{
        method:"POST"
    });

    alert("Transfer successful");
}

async function getTransactions(){

    const id = document.getElementById("transactionAccountId").value;

    const response = await fetch(`${ACCOUNT_API}/${id}/transactions`);

    const transactions = await response.json();

    const list = document.getElementById("transactions");

    list.innerHTML="";

    transactions.forEach(tx=>{

        const item=document.createElement("li");

        item.innerText=
            tx.type+
            " | Amount: "+tx.amount+
            " | Date: "+tx.timestamp;

        list.appendChild(item);
    });
}