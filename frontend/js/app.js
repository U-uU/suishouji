// 后端API基础地址
const API_BASE_URL = 'http://localhost:8088/bills';

// 添加账务
async function addBills() {
    const id = document.getElementById('id').value;
    const name = document.getElementById('name').value;
    const account = document.getElementById('account').value;
    const type = document.getElementById('type').value;
    const total = document.getElementById('total').value;
    const time = document.getElementById('time').value;
    const desc = document.getElementById('desc').value;

    try {
        const response = await fetch(`${API_BASE_URL}/addBills`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ id, name, amount, type, total, time, desc })
        });

        if (response.ok) {
            alert('添加成功');
            // 重新加载账务列表
            loadBills();
        } else {
            alert('添加失败');
        }
    } catch (error) {
        console.error('添加账务错误:', error);
        alert('网络错误');
    }
}

// 加载账务列表
async function loadBills() {
    try {
        const response = (await fetch(`${API_BASE_URL}/select-all`));
        const data = await response.json();

        const tableBody = document.getElementById('billTable');
        tableBody.innerHTML = ''; // 清空现有列表

        data.data.forEach(bill => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <tr>
                    <td>${bill.id}</td>
                    <td>${bill.name}</td>
                    <td>${bill.account}</td>
                    <td>${bill.type}</td>
                    <td>${bill.total}</td>
                    <td>${bill.time}</td>
                    <td>${bill.desc}</td>
                    <td>
                        <button onclick="deleteBill(${bill.id})">删除</button>
                    </td>
                </tr>
            `;
            tableBody.appendChild(row);
        });
    } catch (error) {
        console.error('加载账务错误:', error);
        alert('加载账务失败');
    }
}

// 删除账务
async function deleteBill(billId) {
    try {
        const response = await fetch(`${API_BASE_URL}/delete?${billId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert('删除成功');
            // 重新加载账务列表
            await loadBills();
        } else {
            alert('删除失败');
        }
    } catch (error) {
        console.error('删除账务错误:', error);
        alert('网络错误');
    }
}

// 查询账务
async function searchBills() {
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    const type = document.getElementById('searchType').value;

    try {
        const response = await fetch(`${API_BASE_URL}/bills/search?startDate=${startDate}&endDate=${endDate}&type=${type}`);
        const bills = await response.json();

        const tableBody = document.getElementById('billTableBody');
        tableBody.innerHTML = ''; // 清空现有列表

        bills.forEach(bill => {
            const row = `
                <tr>
                    <td>${bill.id}</td>
                    <td>${bill.name}</td>
                    <td>${bill.amount}</td>
                    <td>${bill.type}</td>
                    <td>${bill.total}</td>
                    <td>${bill.time}</td>
                    <td>${bill.desc}</td>
                    <td>
                        <button onclick="deleteBill(${bill.id})">删除</button>
                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });
    } catch (error) {
        console.error('查询账务错误:', error);
        alert('查询失败');
    }
}

// 页面加载时初始化
document.addEventListener('DOMContentLoaded', () => {
    // 设置默认日期为今天
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('date').value = today;
    document.getElementById('startDate').value = today;
    document.getElementById('endDate').value = today;

    // 加载初始账务列表
    loadBills();
});