<template>
  <div class="member-center-form">
    <h3>會員登入</h3>
    <form @submit.prevent="loginMember">
      <div class="mb-3">
        <label for="email" class="form-label">*Email</label>
        <input
          v-model="email"
          class="form-control email-input"
          type="email"
          required
        />
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">*密碼</label>
        <input
          v-model="password"
          class="form-control password-input"
          type="password"
          required
        />
      </div>
      <button type="submit" class="btn btn-secondary">登入</button>
    </form>

    <!-- <div class="mt-3 text-center">
      <button class="btn btn-success" @click="loginWithLine">
        <font-awesome-icon :icon="['fab', 'line']" class="me-2" />
        登入
      </button>
    </div> -->
    <!-- 返回上一頁的按鈕 -->
    <div class="mt-3 text-center">
      <button @click="goBack" class="btn btn-link">還沒有帳號嗎?註冊</button>
    </div>

    <!-- 忘記密碼按鈕 -->
    <div class="mt-3 text-center">
      <button @click="goToForgetPassword" class="btn btn-link">
        忘記密碼？
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import Swal from "sweetalert2"; // 匯入 SweetAlert2
import { useRouter } from "vue-router"; // 引入 useRouter
import { useAuthStore } from "@/stores/auth"; // 匯入 Pinia Store
import { useOrderStore } from "@/stores/order";
import { API_BASE_URL } from "@/config";

const email = ref("");
const password = ref("");
const router = useRouter(); // 使用 useRouter
const authStore = useAuthStore(); // 使用 Pinia Store

// 登入 LINE 的方法

const loginMember = async () => {
  try {
    const response = await axios.post(`${API_BASE_URL}/api/members/login`, {
      email: email.value,
      password: password.value,
    });

    console.log("登入 API 回應:", response.data);
    const memberId = response.data.memberId;
    const status = response.data.status; // 從回應中獲取狀態
    console.log("取得的 memberId:", memberId);
    console.log("會員狀態:", status);

    if (!memberId) {
      throw new Error("後端未回傳 memberId");
    }

    // 檢查是否是信箱未驗證的情況
    if (status === "error" && response.data.message === "Email not verified") {
      Swal.fire({
        title: "登入失敗",
        text: "您的信箱尚未驗證，請先驗證您的信箱。",
        icon: "warning", // 使用驚嘆號圖示
        confirmButtonText: "確定",
      });
      return;
    }

    // 檢查會員是否為 INACTIVE
    if (status === "INACTIVE") {
      Swal.fire({
        title: "登入失敗",
        text: "您的帳號目前為非活動狀態，請聯絡客服。",
        icon: "warning", // 使用警告圖示
        confirmButtonText: "確定",
      });
      return;
    }

    // 成功回應，請求會員詳細資料
    const url = `${API_BASE_URL}/api/members/details/${memberId}`;
    console.log("請求的 URL:", url);

    const memberDetailsResponse = await axios.get(url);
    const memberDetails = memberDetailsResponse.data;

    // 將會員資料存入 Pinia Store
    authStore.setMemberDetails(memberDetails);
    console.log("取得的會員資料:", memberDetails);

    // 顯示登入成功的訊息
    Swal.fire({
      title: "登入成功！",
      text: `歡迎回來，${memberDetails.fullName}！`,
      icon: "success",
      confirmButtonText: "確定",
    });

    router.push(`/profile/${memberId}`); // 使用 router.push 進行頁面跳轉
  } catch (error) {
    console.error("登入失敗:", error.response || error);
    Swal.fire({
      title: "登入失敗",
      text: error.response?.data?.message || "請確認您的帳號密碼是否正確。",
      icon: "error",
      confirmButtonText: "再試一次",
    });
  }
};

// 返回上一頁
const goBack = () => {
  router.push("/member-center/register"); // 跳回到 /member-center 頁面
};

// 跳轉到忘記密碼頁面
const goToForgetPassword = () => {
  router.push("/forget-password"); // 跳轉到忘記密碼頁面
};
</script>

<style>
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f8f9fa;
}
</style>
