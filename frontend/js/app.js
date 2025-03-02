// 后端API基础地址
const API_BASE_URL = 'http://localhost:8080/suishouji';

// 添加账务
async function addBills() {
    const amount = document.getElementById('amount').value;
    const type = document.getElementById('type').value;
    const date = document.getElementById('date').value;

    try {
        const response = await fetch(`${API_BASE_URL}/src/MainMenu`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ amount, type, date })
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
        const response = await fetch(`${API_BASE_URL}/bills`);
        const bills = await response.json();

        const tableBody = document.getElementById('billTableBody');
        tableBody.innerHTML = ''; // 清空现有列表

        bills.forEach(bill => {
            const row = `
                <tr>
                    <td>${bill.amount}</td>
                    <td>${bill.type}</td>
                    <td>${bill.date}</td>
                    <td>
                        <button onclick="deleteBill(${bill.id})">删除</button>
                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });
    } catch (error) {
        console.error('加载账务错误:', error);
        alert('加载账务失败');
    }
}

// 删除账务
async function deleteBill(billId) {
    try {
        const response = await fetch(`${API_BASE_URL}/bills/${billId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert('删除成功');
            // 重新加载账务列表
            loadBills();
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
                    <td>${bill.amount}</td>
                    <td>${bill.type}</td>
                    <td>${bill.date}</td>
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