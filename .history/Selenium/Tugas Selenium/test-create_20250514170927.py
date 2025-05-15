from selenium import webdriver 
from selenium.webdriver.common.by import By 
from selenium.webdriver.firefox.service import Service
from webdriver_manager.firefox import GeckoDriverManager
import time 

# Fungsi utama untuk menguji create user
def test_create_user(name, username, password, expected_success): 
    # --- 1. Setup: Membuka browser Firefox --- 
    service = Service(GeckoDriverManager().install())
    driver = webdriver.Firefox(service=service)

    try: 
        # --- 2. Akses halaman create user --- 
        driver.get("http://localhost:3000/users.html")
        time.sleep(1)

        listUserBefore = driver.find_element(By.ID, "userList").text
        dataUserBefore = listUserBefore.split("\n")
        lengthListUserBefore = len(dataUserBefore)

        # --- 3. Isi form create user --- 
        driver.find_element(By.ID, "name").send_keys(name)      
        driver.find_element(By.ID, "username").send_keys(username)      
        driver.find_element(By.ID, "password").send_keys(password)      
        
        # --- 4. Klik tombol create user --- 
        driver.find_element(By.CSS_SELECTOR, "form#userForm button[type='submit']").click()
        time.sleep(1) 

        # --- 5. Ambil list user dari halaman --- 
        listUserAfter = driver.find_element(By.ID, "userList").text
        dataUserAfter = listUserAfter.split("\n")
        lengthListUserAfter = len(dataUserAfter)

        # --- 6. Validasi hasil create user sesuai skenario --- 
        if expected_success: 
            assert f"{name}" in listUserAfter and lengthListUserBefore + 1 == lengthListUserAfter, "❌ Pesan sukses tidak ditemukan" 
            print("✅ Create user sukses - PASSED") 
        else: 
            assert f"{name}" not in listUserAfter or lengthListUserBefore == lengthListUserAfter, "❌ Pesan error tidak sesuai" 
            print("✅ Create user gagal terdeteksi - PASSED")

    except AssertionError as e: 
        print("❌ TEST GAGAL:", e) 
    finally: 
        # --- 7. Tutup browser --- 
        driver.quit()

--- Menjalankan semua skenario praktikum ---
print("🧪 Skenario 1: Create user berhasil")
test_create_user("nuel", "nuel", "12345", expected_success=True)

print("🧪 Skenario 2: name kosong")
test_create_user("", "nuel", "12345", expected_success=False)

print("🧪 Skenario 3: Username kosong")
test_create_user("nuel", "", "12345", expected_success=False)

print("🧪 Skenario 4: Password kosong")
test_create_user("nuel", "nuel", "", expected_success=False)

print("🧪 Skenario 5: Name, Username & Password kosong")
test_create_user("", "", "", expected_success=False)
