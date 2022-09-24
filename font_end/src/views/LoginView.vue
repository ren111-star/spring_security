<template>
  <contentBase>
    <div class="row justify-content-md-center">
      <div class="col-3">
        <form @submit.prevent="login">
          <div class="mb-3">
            <label for="username" class="form-label">username</label>
            <input type="text" v-model="username" class="form-control" id="username"/>
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">password</label>
            <input type="password" v-model="password" class="form-control" id="password"/>
          </div>
          <div class="error_message">{{error_message}}</div>
          <button type="submit" class="btn btn-primary">login</button>
        </form>
      </div>
    </div>
  </contentBase>
</template>

<script>
import contentBase from '@/components/ContentBase.vue';
import { ref } from 'vue'
import axios from 'axios'

export default {
    components: { contentBase },
    setup() {
      let username = ref('');
      let password = ref('');
      let error_message = ref('');
      function jsonToQueryString(json) {
          return  Object.keys(json).map(function(key) {
              return encodeURIComponent(key) + '=' +
                  encodeURIComponent(json[key]);
          }).join('&');
      }

      const login = () => {
        // console.log(username, password)
        axios.post('/before/api/login',
        jsonToQueryString ({
          username: username.value,
          password: password.value
        }))
        .then((response) => {
          console.log(response.data)
        })
      }

      return{
        username,
        password,
        error_message,
        login
      }
    }
}
</script>
<style scoped>
button {
  width: 100%;
}
.error_message{
  color: red;
}
</style>
