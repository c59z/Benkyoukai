<template>
  <div class="container my-footer">
    <footer class="py-3 my-4">
      <ul class="nav justify-content-center border-bottom pb-3 mb-3">
        <li class="nav-item"><a href="https://github.com/c59z" target="_blank" class="nav-link px-2 text-muted">GitHub</a></li>
      </ul>
      <p class="text-center text-muted">&copy; 2024 All rights reserved.</p>
    </footer>
  </div>
</template>

<script>
export default {
  name: "Footer"
}
</script>

<style scoped>
  .my-footer {
    background-color: #f57a4f;
    max-width: 100%;
    padding: 0;
    margin: 0 auto;
  }

  footer > p , ul > li > a {
    color: white !important;
  }

  footer {
    margin: 0 auto !important;
  }



</style>