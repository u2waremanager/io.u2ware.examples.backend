<template>
  <v-form :class="class" validate-on="eager" @update:model-value="formValidate">
    <v-data-table
      class="py-4 highlight-row"
      :headers="headers"
      :items="modelValue"
      density="compact"
      hide-default-footer
    >

      <template v-slot:header.actions="{ item }">
        <v-btn
          size="x-small"
          text
          variant="elevated"
          @click="addItem()"
          :disabled="!editable"
        >
          <v-icon>mdi-plus</v-icon>
        </v-btn>
      </template>


      <template v-for="header in itemHeaders" v-slot:[`item.${header.key}`]="{ item }">

        <span v-if="'remove' == item.actions">{{ item[header.key] }}</span>

        <v-text-field
          v-if="'add' == item.actions"
          v-model="item[header.key]"
          :rules="[$rules.requried]"
          class="mx-0"
          :placeholder="header.title"
          variant="outlined"
          density="compact"
          hide-details
        ></v-text-field> 

      </template>


      <template v-slot:item.actions="{ item }">
        <v-btn
          v-if="'add' == item.actions"
          size="x-small"
          text
          variant="elevated"
          @click="add(item)"
          :disabled="!validate"
        >
          <v-icon>mdi-plus</v-icon>
        </v-btn>
        <v-btn
          v-if="'add' == item.actions"
          size="x-small"
          text
          variant="elevated"
          @click="cancel(item)"
        >
          <v-icon>mdi-cancel</v-icon>
        </v-btn>

        <v-btn
          v-if="'remove' == item.actions"
          size="x-small"
          text
          variant="elevated"
          :disabled="!editable"
          @click="remove(item)"
        >
          <v-icon>mdi-cancel</v-icon>
        </v-btn>
      </template>

    </v-data-table>
    </v-form>



</template>

<script>
export default {
  props: {
    modelValue: {
      type: Object,
      default: [],
    },
    class: {
      type: String,
      default: "ma-0",
    },

    itemHeaders: {
      type: Array,
      default: [],
    },
  },

  data: () => ({
    // items: [],
    headers: [],

    // headers: [
    //   { key: "key", title: "key", align: "start", width:"40%" },
    //   { key: "value", title: "value", align: "start", width:"40%" },
    //   { key: "actions", title: "Actions", align: "end", width:"20%" },
    // ],
    // sortBy: [{ key: "key", order: "asc" }],

    editable: true,
    validate: false,
  }),

  mounted() {
    console.log("mounted", this.modelValue);

    this.headers = this.itemHeaders.concat(
      { key: "actions", title: "Actions", align: "end", width:"20%" }
    );
    this.addItems();
  },


  methods: {
    formValidate(r) {
      this.validate = r;
    },

    addItems() {
        this.modelValue.forEach(item => {
            item.actions = "remove";
        });
        this.$emit('update:modelValue', this.modelValue);
    },


    addItem() {
      // console.log("addItem");

      const newItem = {};
      this.headers.forEach(header => {
        newItem[header.key] = (header.key !== 'actions') ? "" : "add";
      });

      this.modelValue.push(newItem);
      this.$emit('update:modelValue', this.modelValue);
      this.editable = false;
    },

    cancel(item) {
      // console.log("cancel", 1, item);
      let idx = this.modelValue.findIndex((e) => e.actions == "add");
      if (idx > -1) this.modelValue.splice(idx, 1);
      this.$emit('update:modelValue', this.modelValue);
      this.editable = true;
    },

    add(item) {
      item.actions = "remove";
      this.$emit('update:modelValue', this.modelValue);
      this.editable = true;
    },

    remove(item) {
      // console.log("add", 1, item);

      let idx = this.modelValue.findIndex((e) => e.key == item.key);
      if (idx > -1) this.modelValue.splice(idx, 1);
      this.$emit('update:modelValue', this.modelValue);
      this.editable = true;
    },


  },
};
</script>

<style scoped>
.highlight-row {
  border: 1px solid gray;
}
</style>