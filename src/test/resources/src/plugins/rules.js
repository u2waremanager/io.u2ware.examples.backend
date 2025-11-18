
import $i18n from "./locales";
const $t = $i18n.global.t;


export default {
  install: (app, options) => {
 
    const _rules = {

        requried : (value) =>{
            if (value) return true
            return $t("rules.requried.message");
        }

    }


    app.config.globalProperties.$rules = _rules; 
    app.provide('$rules', _rules)                

  }
}