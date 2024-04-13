<template>
	<nav class="flex justify-between h-20 bg-baylor-green items-center">
		<div class="flex px-6">
			<NuxtLink to="/">Baylor Logo</NuxtLink>
		</div>
		<ul class="flex flex-1 justify-center">
			<li class="px-20">
				<NuxtLink to="/">Home</NuxtLink>
			</li>
			<li class="px-20">
				<NuxtLink to="/about">About</NuxtLink>
			</li>
			<li class="px-20">
				<NuxtLink to="/editor">Editor</NuxtLink>
			</li>
			<li class="px-20">
				<NuxtLink to="/posts">Posts</NuxtLink>
			</li>
		</ul>
		<div class="flex px-6">
			<NuxtLink to="/login">Login</NuxtLink>
		</div>
	</nav>
</template>

<style lang="scss">
a {
	@apply text-baylor-gold;
}
</style>
