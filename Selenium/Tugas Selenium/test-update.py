from selenium import webdriver 
from selenium.webdriver.common.by import By 
from selenium.webdriver.firefox.service import Service
from webdriver_manager.firefox import GeckoDriverManager
import time 

# Fungsi utama untuk menguji update user
def test_update_user(id, newName, expected_success): 

    # --- 1. Setup: Membuka browser Firefox ---
    service = Service(GeckoDriverManager().install())
    driver = webdriver.Firefox(service=service)

    try: 

        # --- 2. Akses halaman update user ---
        driver.get("http://localhost:3000/users.html")
        time.sleep(1)

        # --- 3. Ambil list user dari halaman ---
        listUserBefore = driver.find_element(By.ID, "userList").text
        dataUserBefore = listUserBefore.split("\n")
        lengthListUserBefore = len(dataUserBefore)

        # --- 4. Klik tombol update user ---
        try:
            updateButton = driver.find_element(By.CSS_SELECTOR, f"button[onclick='edit({id})']")
            updateButton.click()
        except:
            if expected_success:
                raise Exception("❌ Tombol edit tidak ditemukan padahal seharusnya berhasil.")
            else:
                print("✅ Update user gagal terdeteksi - PASSED (tombol tidak ditemukan)")
                return

        # --- 5. Isi prompt nama baru ---
        alert = driver.switch_to.alert
        alert.send_keys(newName)
        alert.accept() 

        time.sleep(1.5)

        # --- 6. Ambil ulang list user setelah update ---
        listUserAfter = driver.find_element(By.ID, "userList").text
        dataUserAfter = listUserAfter.split("\n")
        lengthListUserAfter = len(dataUserAfter)

        # --- 7. Validasi ---
        if expected_success:
            assert newName in listUserAfter and lengthListUserAfter == lengthListUserBefore, "❌ Update gagal"
            print("✅ Update user sukses - PASSED")
        else:
            assert newName not in listUserAfter and lengthListUserAfter == lengthListUserBefore, "❌ Update seharusnya gagal"
            print("✅ Update user gagal terdeteksi - PASSED")

    except AssertionError as e:
        print(e)
    finally:
        driver.quit()


# --- Menjalankan semua skenario praktikum ---
print("🧪 Skenario 1: Update user berhasil")
test_update_user(2, "nuel", expected_success=True)
# print("🧪 Skenario 2: Update user gagal")
# test_update_user(2, "", expected_success=False)
print("🧪 Skenario 2: Update user gagal")
test_update_user(100, "cuih", expected_success=False)


