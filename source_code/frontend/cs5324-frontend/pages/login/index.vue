<template>
	<div class="min-h-screen flex flex-col">
		<Navigation />
		<div class="flex-grow flex flex-col justify-center items-center">
			<div class="border-2 border-black w-1/3 flex flex-col items-center login-wrapper">
				<div class="flex flex-col mt-24 pb-16">
					<label for="username">Username</label>
					<input type="text" id="username" class="border-2 border-black rounded-md w-72 h-8" />
				</div>

				<div class="flex flex-col pb-24">
					<label for="password">Password</label>
					<input type="text" id="username" class="border-2 border-black rounded-md w-72 h-8" />
				</div>

				<button class="h-12 w-36 rounded-xl bg-baylor-green font-bold text-baylor-gold">Login</button>
			</div>
		</div>
	</div>
</template>

<style>
.login-wrapper {
	height: 36rem;
}
</style>
