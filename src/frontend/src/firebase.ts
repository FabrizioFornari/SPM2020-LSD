import store from '@/store/auth'
import router from '@/router'
import config from '@/config/firebase'
import Firebase from 'firebase/app'
import 'firebase/auth'
import 'firebase/firestore'

export const fireApp = Firebase.initializeApp(config)
export const fireAuth = Firebase.auth()
export const fireStore = Firebase.firestore()
export const geoPoint = Firebase.firestore.GeoPoint

const onAuthStateChangedPromise = new Promise((resolve, reject) => {
    fireAuth.onAuthStateChanged(user => {
        store.dispatch("fetchUser", user)
        resolve(user)
    }, err => {
        reject(err)
    })
})

export async function signin(email: string, password: string, name: string, surname: string) {
    try {
        await fireAuth.createUserWithEmailAndPassword(email, password)
        const current = fireAuth.currentUser
        if (current) {
            await current.updateProfile({
                displayName: name
            })
            fireStore.collection("Users").doc(store.getters.user.uid).set({
                "id": store.getters.user.uid,
                "name": name,
                "surname": surname,
                "email": email
            })
            store.dispatch("fetchUser", fireAuth.currentUser)
        }
        console.log("Registred")
    } catch (error) {
        console.log(error)
    }
}

export async function login(email: string, password: string) {
    try {
        await fireAuth.signInWithEmailAndPassword(email, password)
        console.log("Logged in")
    } catch (error) {
        console.log(error)
    }
}

export async function logout() {
    try {
        await fireAuth.signOut()
        console.log("Logged out")
        router.push('/')
    } catch (error) {
        console.log(error)
    }
}
  
export const onAuthStateInit = () => onAuthStateChangedPromise