<script setup lang="ts">
import { registrationService } from '@/services';
import { onMounted } from 'vue';
import { ref } from 'vue';
import { Ref } from 'vue';

interface VerificationProps {
  token: string;
}
const props = defineProps<VerificationProps>();
const status: Ref<boolean> = ref<boolean>(false);

onMounted(async () => {
  status.value = await registrationService.verify(props.token);
});
</script>
<template>
  <div>
    <p v-if="status">Successful account verficiation!</p>
    <p v-else>Account verification failed!</p>
  </div>
</template>

<style scoped>
p {
  color: var(--gr-primary);
}
</style>
