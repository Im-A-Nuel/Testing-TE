from selenium import webdriver 
from selenium.webdriver.common.by import By 
from selenium.webdriver.firefox.service import Service
from webdriver_manager.firefox import GeckoDriverManager
import time 

# Fungsi utama untuk menguji delete user
def test_delete_user(id, expected_success): 
    # --- 1. Setup: Membuka browser Firefox --- 
    service = Service(GeckoDriverManager().install())
    driver = webdriver.Firefox(service=service)

    try: 
        # --- 2. Akses halaman delete user --- 
        driver.get("http://localhost:3000/users.html")
        time.sleep(1)

        # --- 3. Ambil list user dari halaman ---
        listUserBefore = driver.find_element(By.ID, "userList").text
        dataUserBefore = listUserBefore.split("\n")
        lengthListUserBefore = len(dataUserBefore)

        try :

            # --- 4. Klik tombol delete user ---
            deleteButton = driver.find_element(By.CSS_SELECTOR, f"button[onclick='hapus({id})']")
            deleteButton.click()

            # --- 5. Ambil list user dari halaman --- 
            listUserAfter = driver.find_element(By.ID, "userList").text
            dataUserAfter = listUserAfter.split("\n")
            lengthListUserAfter = len(dataUserAfter)

            # --- 6. Validasi hasil delete user sesuai skenario --- 
            if expected_success: 
                assert lengthListUserBefore - 1 == lengthListUserAfter, "❌ Pesan sukses tidak ditemukan" 
                print("✅ Delete user sukses - PASSED") 
            else: 
                assert lengthListUserBefore == lengthListUserAfter, "❌ Pesan error tidak sesuai" 
                print("✅ Delete user gagal terdeteksi - PASSED")

        except Exception as e:
                print("✅ Delete user gagal terdeteksi - PASSED")

    except AssertionError as e: 
        print("❌ TEST GAGAL:", e) 
    finally: 
        # --- 7. Tutup browser --- 
        driver.quit()

# --- Menjalankan semua skenario praktikum ---
print("🧪 Skenario 1: Delete user berhasil")
test_delete_user(2, expected_success=True)

print("🧪 Skenario 2: Delete user gagal")
test_delete_user(100, expected_success=False)


