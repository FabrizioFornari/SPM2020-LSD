<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card">
          <div class="card-body">
            Registrati come
            <select id="mySelect" v-model="type">
              <option value="0">Utente</option>
              <option value="1">Agente di Polizia</option>
              <option value="2">Comune</option>
            </select>
            <br><br>
            <form id ="form1" v-if="type == '0'">
            <div class="form-group row">
                <label for="nameUser" class="col-md-4 col-form-label text-md-right">Nome</label>
                <div class="col-md-6">
                  <input id="nameUser" type="text" class="form-control" name="nameUser" value required autofocus
                    v-model="form.nameUser"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="surnameUser" class="col-md-4 col-form-label text-md-right">Cognome</label>
                <div class="col-md-6">
                  <input id="surnameUser" type="text" class="form-control" name="surnameUser" value required autofocus
                    v-model="form.surnameUser"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="emailUser" class="col-md-4 col-form-label text-md-right">Email</label>
                <div class="col-md-6">
                  <input id="emailUser" type="email" class="form-control" name="emailUser" value required autofocus
                    v-model="form.emailUser"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="passwordUser" class="col-md-4 col-form-label text-md-right">Password</label>
                <div class="col-md-6">
                  <input id="passwordUser" type="password" class="form-control" name="passwordUser" required
                    v-model="form.passwordUser"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="passwordUserConfirm" class="col-md-4 col-form-label text-md-right">Conferma password</label>
                <div class="col-md-6">
                  <input id="passwordUserConfirm" type="password" class="form-control" name="passwordUserConfirm" required
                    v-model="form.passwordUserConfirm"/>
                </div>
              </div>
            </form>
            <form id ="form2" v-if="type == '1'">
            <div class="form-group row">
                <label for="namePolice" class="col-md-4 col-form-label text-md-right">Nome</label>
                <div class="col-md-6">
                  <input id="namePolice" type="text" class="form-control" name="namePolice" value required autofocus
                    v-model="form.namePolice"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="surnamePolice" class="col-md-4 col-form-label text-md-right">Cognome</label>
                <div class="col-md-6">
                  <input id="surnamePolice" type="text" class="form-control" name="surnamePolice" value required autofocus
                    v-model="form.surnamePolice"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="districtPolice" class="col-md-4 col-form-label text-md-right">Dipartimento</label>
                <div class="col-md-6">
                  <input id="districtPolice" type="text" class="form-control" name="districtPolice" value required autofocus
                    v-model="form.districtPolice"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="emailPolice" class="col-md-4 col-form-label text-md-right">Email</label>
                <div class="col-md-6">
                  <input id="emailPolice" type="email" class="form-control" name="emailPolice" value required autofocus
                    v-model="form.emailPolice"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="passwordPolice" class="col-md-4 col-form-label text-md-right">Password</label>
                <div class="col-md-6">
                  <input id="passwordPolice" type="password" class="form-control" name="passwordPolice" required
                    v-model="form.passwordPolice"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="passwordPoliceConfirm" class="col-md-4 col-form-label text-md-right">Conferma password</label>
                <div class="col-md-6">
                  <input id="passwordPoliceConfirm" type="password" class="form-control" name="passwordPoliceConfirm" required
                    v-model="form.passwordPoliceConfirm"/>
                </div>
              </div>
            </form>
            <form id ="form3" v-if="type == '2'">
            <div class="form-group row">
                <label for="nameMunicipality" class="col-md-4 col-form-label text-md-right">Nome</label>
                <div class="col-md-6">
                  <input id="nameMunicipality" type="text" class="form-control" name="nameMunicipality" value required autofocus
                    v-model="form.nameMunicipality"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="districtMunicipality" class="col-md-4 col-form-label text-md-right">Città</label>
                <div class="col-md-6">
                  <input id="districtMunicipality" type="text" class="form-control" name="districtMunicipality" value required autofocus
                    v-model="form.districtMunicipality"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="emailMunicipality" class="col-md-4 col-form-label text-md-right">Email</label>
                <div class="col-md-6">
                  <input id="emailMunicipality" type="email" class="form-control" name="emailMunicipality" value required autofocus
                    v-model="form.emailMunicipality"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="passwordMunicipality" class="col-md-4 col-form-label text-md-right">Password</label>
                <div class="col-md-6">
                  <input id="passwordMunicipality" type="password" class="form-control" name="passwordMunicipality" required
                    v-model="form.passwordMunicipality"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="passwordMunicipalityConfirm" class="col-md-4 col-form-label text-md-right">Conferma password</label>
                <div class="col-md-6">
                  <input id="passwordConfirmMunicipality" type="password" class="form-control" name="passwordMunicipalityConfirm" required
                    v-model="form.passwordConfirmMunicipality"/>
                </div>
              </div>
            </form>
            <div class="form-group row mb-0">
              <div class="col-md-12">
                <button type="submit" class="btn btn-primary">Registrati</button>
                <br><br>
                <p><router-link to="/login">Sei già registrato?</router-link></p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
	
import { login } from '@/firebase';

export default {
  data() {
    return {
      type: "0",
      form: {
        email: "",
        password: ""
      }
    };
  },
  methods: {
    async signIn() {
      await login(this.form.email, this.form.password)
      this.$router.push(this.$route.query.redirect || '/')
      this.form.email = ""
      this.form.password = ""
    }
  }
};
</script>