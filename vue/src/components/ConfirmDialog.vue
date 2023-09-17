<script setup lang="ts">
interface ConfirmProps {
  title?: string;
  text?: string;
}

const props = defineProps<ConfirmProps>();
const emit = defineEmits<{
  (event: 'no'): void;
  (event: 'yes'): void;
}>();
</script>

<template>
  <v-card class="dialog-card">
    <v-card-title>{{ props.title }}</v-card-title>
    <v-card-text>{{ props.text }}</v-card-text>
    <v-card-actions>
      <v-btn class="ui-action" color="background" @click="$emit('no')">Cancel</v-btn>
      <v-btn class="ui-action" color="warning" @click="$emit('yes')">Ok</v-btn>
    </v-card-actions>
  </v-card>
</template>
