<template>
    <v-container class="text-center">
        <v-progress-circular color="primary" indeterminate>
        </v-progress-circular>
    </v-container>

    <!-- <pre>{{ oauth2 }}</pre>
    <pre>{{ token }}</pre> -->
</template>

<script>
const x = "[/accounts/logon]";
import $accountsApi from "@/assets/apis/accounts";
import $accountsStore from "@/assets/stores/accounts.js";

import $commonStore from "@/assets/stores/common.js";

export default {

  computed: {
    oauth2: $commonStore.computed.oauth2,
    token : $commonStore.computed.token,    
  },


    mounted() {

        this.subtitle = this.$t("accounts.login.title");


        Promise.resolve()
            .then((r) => {
                console.log(x, "mounted()", 1);
                return $accountsApi.oauth2.userinfo();
            })
            .then((r) => {
                console.log(x, "mounted()", 2);
                return this.$router.push("/");
            })
            .catch((r) => {
                // OK. action !!
                console.log(x, "mounted()", 3, this.$route.query);
                return $accountsApi.oauth2.login(this.$route.query);
            })
            .then((r) => {
                console.log(x, "mounted()", 4, r);
                $accountsStore.computed.userinfo.set(r);
                this.$router.push("/contents");
            })
            .catch((r) => {
                console.log(x, "mounted()", 5, r);
                this.$router.push("/");
            });
    }

}
</script>