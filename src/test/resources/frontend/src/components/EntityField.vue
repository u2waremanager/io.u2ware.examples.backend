<template>
  <v-autocomplete
    v-model="select"
    v-model:search="search"
    :items="items"
    :item-title="itemTitle"
    :item-value="itemValue"
    :loading="loading"
    :multiple="multiple"
    autocomplete="off"
    @update:modelValue="updateModelValue"
  ></v-autocomplete>
</template>
<script>
const x = "EntityField"

export default {
  props: {
    modelValue: {
      type: Object,
      // default: {},
    },

    loading: {
      type: Boolean,
      default: false,
    },

    multiple : {
      type: Boolean,
      default: false,
    },

    items: {
      type: Array,
      default: [],
    },   
    itemTitle: {
      type: String,
      default: "",
    },      
    itemValue: {
      type: String,
      default: "",
    }, 
    itemSelected: {
      type: Object,
      // default: {},
    },    
    

  },

  data: () => ({
    select: undefined,
    search: undefined,
  }),

  watch: {
    search: {
      handler(val) {
        // console.log(x, "search", val);
        this.$emit('querySelections', val);
      },
    },
  },

  mounted() {
    // console.log(x, "mounted", 1, this.modelValue, this.multiple);

    if(this.itemSelected){
      if(this.multiple){
        // console.log(x, "mounted", 2);
        this.select = this.itemSelected.map(item => item[this.itemValue]);
      }else{
        // console.log(x, "mounted", 3);
        this.select = this.itemSelected[this.itemValue];
      }
    }
  },

  emits: ["querySelections", "update:modelValue"],

  methods: {
    querySelections(v) {
      // console.log(x, "querySelections", v);
      this.$emit('querySelections', v);
    },

    updateModelValue(){
      // console.log(x, "updateModelValue", this.select);

      if(this.multiple){
        const selectedItems = [];
        this.items.forEach(item => {
          this.select.forEach(sel => {
            if(item[this.itemValue] === sel){
              selectedItems.push(item);
            }
          });
        });
        this.$emit('update:modelValue', selectedItems);

      } else{
        this.items.forEach(item => {
          if(item[this.itemValue] === this.select){
            this.$emit('update:modelValue', item);
          }
        });
      }
    }
  },
};
</script>
