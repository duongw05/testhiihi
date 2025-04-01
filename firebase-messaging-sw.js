importScripts('https://www.gstatic.com/firebasejs/10.12.2/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/10.12.2/firebase-messaging-compat.js');

firebase.initializeApp({
    apiKey: "AIzaSyDIMOedVvYEou3CPFakGdkcJl0weQZ8A7A",
    authDomain: "test-project-crm-3ed8b.firebaseapp.com",
    projectId: "test-project-crm-3ed8b",
    storageBucket: "test-project-crm-3ed8b.appspot.com",
    messagingSenderId: "893736430591",
    appId: "1:893736430591:web:fa666f253184bf4fb9d5ca",
    measurementId: "G-XEDPT1S7JJ"
});

const messaging = firebase.messaging();

messaging.onBackgroundMessage((payload) => {
    console.log('Received background message ', payload);
    // Customize notification here
    const notificationTitle = payload.notification.title;
    const notificationOptions = {
        body: payload.notification.body,
        icon: '/firebase-logo.png'
    };

    self.registration.showNotification(notificationTitle,
        notificationOptions);
});

