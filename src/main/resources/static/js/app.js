const API = "http://localhost:8085/api/accounts";


async function createAccount() {

    const fullName = document.getElementById("fullName").value;
    const email = document.getElementById("email").value;

    const response = await fetch(`/api/accounts/create?fullName=${fullName}&email=${email}`, {
        method: "POST"
    });

    const data = await response.json();

    document.getElementById("createResult").innerText =
        "Account created with ID: " + data.id +
        " | Balance: " + data.balance;
}

async function getAccount() {

    const id = document.getElementById("accountId").value;

    const response = await fetch(`${API}/${id}`);

    const data = await response.json();

    document.getElementById("balance").innerText =
        "Balance: R" + data.balance;
}

async function deposit() {

    const id = document.getElementById("depositAccountId").value;
    const amount = document.getElementById("depositAmount").value;

    await fetch(`${API}/${id}/deposit?amount=${amount}`, {
        method: "POST"
    });

    alert("Deposit successful");
}

async function withdraw() {

    const id = document.getElementById("withdrawAccountId").value;
    const amount = document.getElementById("withdrawAmount").value;

    await fetch(`${API}/${id}/withdraw?amount=${amount}`, {
        method: "POST"
    });

    alert("Withdrawal successful");
}

async function transfer() {

    const from = document.getElementById("fromAccount").value;
    const to = document.getElementById("toAccount").value;
    const amount = document.getElementById("transferAmount").value;

    await fetch(`${API}/transfer?from=${from}&to=${to}&amount=${amount}`, {
        method: "POST"
    });

    alert("Transfer successful");
}

async function getTransactions() {

    const id = document.getElementById("transactionAccountId").value;

    const response = await fetch(`/api/accounts/${id}/transactions`);

    const transactions = await response.json();

    const list = document.getElementById("transactions");
    list.innerHTML = "";

    transactions.forEach(tx => {

        const item = document.createElement("li");

        item.innerText =
            tx.type +
            " | Amount: " + tx.amount +
            " | Date: " + tx.timestamp;

        list.appendChild(item);
    });
}