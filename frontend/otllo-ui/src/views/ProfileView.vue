<script setup>
import { useAuthStore } from "../store";

const store = useAuthStore();

const user = store.user;
</script>

<template>
  <div class="bg-slate-100">
    <div class="flex min-h-full flex-row">
      <div class="px-10 py-4 md:w-2/3">
        <div class="flex flex-row border rounded-lg bg-slate-50 pb-2">
          <!--<div>Background Image</div>
          <div>Profile Image</div>-->
          <div class="pl-3 pt-4">
            <p>{{ user.firstName }} {{ user.lastName }}</p>
            <p>{{ user.username }}</p>
          </div>
          <div class="m-0 ml-auto">
            <div class="flex flex-row">
              <div>
                <button
                  class="border-2 border-slate-800 bg-slate-800 rounded-full m-2 px-4 py-0.5"
                  @click="console.log('Connections Button')"
                >
                  <span class="text-center text-white font-medium"
                    >Connections</span
                  >
                </button>
              </div>
              <div>
                <button
                  class="border-2 border-slate-800 rounded-full m-2 px-4 py-0.5"
                  @click="console.log('Edit Button')"
                >
                  <span class="text-center text-slate-800 font-medium">
                    Edit
                  </span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="py-4 pr-10 md:w-1/3">
        <div class="border rounded-lg bg-slate-50 pb-2">
          <div class="w-full grid grid-cols-1 divide-y">
            <div class="pl-4 py-2">
              <span>Info</span>
            </div>
            <div class="pl-4 py-2">
              <ul>
                <li>
                  <span>Email: {{ user.email }}</span>
                </li>
                <li>
                  <span>Phone Number: {{ user.phoneNumber }}</span>
                </li>
                <li>
                  <span>Location: {{ "London" }}</span>
                </li>
                <li>
                  <span>Connections: {{ "200" }}</span>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="px-10">
      <div class="w-full border rounded-lg bg-slate-50 pb-2">
        <div class="grid grid-cols-1 divide-y">
          <div class="pl-4 py-2">
            <span>About</span>
          </div>
          <div class="pl-4 py-4">
            <span>{{ user.status }}</span>
          </div>
        </div>
      </div>
    </div>

    <div></div>
  </div>
</template>
