<template>
  <v-container class="text-center">
    <v-progress-circular color="primary" indeterminate> </v-progress-circular>
  </v-container>
  <!-- 
<pre>{{ oauth2 }}</pre>
<pre>{{ token }}</pre>
-->

</template>

<script>
const x = "[/accounts/logon]";
import $oauth2Server from "@/assets/backend/oauth2-server";
import $userinfo from "@/assets/stores/userinfo.js";

export default {
  computed: {
    oauth2: $userinfo.computed.oauth2,
    token: $userinfo.computed.token,
  },

  mounted() {
    this.subtitle = this.$t("accounts.login.title");

    Promise.resolve()
      .then((r) => {
        console.log(x, "mounted()", 1);
        return $oauth2Server.oauth2.userinfo();
      })
      .then((r) => {
        console.log(x, "mounted()", 2);
        this.$router.push("/contents");
      })
      .catch((r) => {
        // OK. action !!
        console.log(x, "mounted()", 3, this.$route.query);
        return $oauth2Server.oauth2.login(this.$route.query);
      })
      .then((r) => {
        console.log(x, "mounted()", 4, r);
        this.$router.push("/contents");
      })
      .catch((r) => {
        console.log(x, "mounted()", 5, r);
        this.$router.push("/");
      });
  },
};
</script>
