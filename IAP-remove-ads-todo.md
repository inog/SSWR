Here’s your text reformatted into clean, structured Markdown for readability and easy sharing.

***

# Implementing In-App Purchases (IAP) for the "Remove Ads" Feature

To implement In-App Purchases (IAP) for your **"Remove Ads"** feature, follow these steps in the Google Play Console:

***

## 1. Set Up Your Merchant Account

If you haven't already, you must set up a **Google Payments Merchant Account** to receive payments.

- Go to **Settings > Payment profile** and follow the instructions to link or create a profile.

***

## 2. Create the In-App Product

Since you've integrated the `BillingManager` with the ID `remove_ads`, you must match this exactly in the console.

1. Select your app in the **Play Console**.
2. In the left menu, go to **Monetize > Products > In-app products**.
3. Click **Create product**.
4. **Product ID:** Enter `remove_ads` (this must match the `PRODUCT_ID_PRO` in your `BillingManager.kt`).
5. **Product details:**
    - **Name:** e.g., *Remove Ads* or *Werbung entfernen*
    - **Description:** e.g., *One-time purchase to remove all advertisements.*
6. **Price:** Set your desired price (e.g., €0.99).
7. Click **Save**, then **Activate**.

***

## 3. Add License Testers *(Crucial for Testing)*

To test the purchase flow without spending real money:

1. Go to the **Play Console Home** (all apps view).
2. Navigate to **Settings > License testing**.
3. Add your own **Gmail address** (the one used on your testing device) to the list of testers.
4. Set **License response** to `RESPOND_NORMALLY`.

***

## 4. Upload the App to a Testing Track

Google Play Billing will not work until the app is known by the Play Store.

1. Build a **signed APK or App Bundle** with the new code.
2. Go to **Testing > Internal testing** (or **Closed testing**).
3. Create a new release and upload your bundle.
4. Add your email address to the **Testers** tab of that track.
5. **Important:** Copy the *"Join on the web"* or *"Join on Android"* link provided in the Testers tab and open it on your device to officially **opt-in** to the test.

---

## 5. Test on a Physical Device

- Use a **physical device** (emulators often have issues with the Play Store).
- Ensure the device is logged in with the **Gmail address** added to License Testing.
- When you click *"Werbung entfernen"* in your app, you should see a **Google Play bottom sheet**.
    - If everything is set up correctly, it will say *"Test card, always approves."*

***

### Troubleshooting Common Issues

- **"Item not found":** The Product ID `remove_ads` might not be activated yet, or your `applicationId` differs from what's in the console.
- **Billing setup failed:** Ensure the version of the app on your device matches the **version code** uploaded to the testing track.

---

Would you like me to make this Markdown version more concise for documentation (e.g., a README.md style)?