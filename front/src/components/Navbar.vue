<script setup lang="ts"/>
<template>
  <nav class="nav">
    <router-link to="/profession" class="nav-link">Профессии</router-link>
    <router-link to="/department" class="nav-link">Отделы</router-link>
    <router-link to="/employee" class="nav-link">Сотрудники</router-link>
  </nav>
</template>

<style scoped>
.nav {
  display: flex;
  gap: 8px;
}
.nav-link {
  color: deepskyblue;
  text-decoration: none;
}
.nav-link:hover {
  color: blueviolet;
}
.router-link-active {
  color: blueviolet;
}
</style>
