{
  description = "A very basic flake";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable";
  };

	nixConfig.allowUnfree = true;

  outputs = { self, nixpkgs  , ... }:
	let
	system = "x86_64-linux";
	pkgs = import nixpkgs {
    system = "x86_64-linux";
    config = { allowUnfree = true; };
  };

	in
	{
			devShells.${system}.androidDev = pkgs.mkShell {
			buildInputs = with pkgs; [
			  gcc
				libcxx
			  jdk
			  java-language-server
			  kotlin
				kotlin-language-server
				gnumake
				android-tools
				android-studio-tools
			];

			shellHook = ''
			export JAVA_HOME=${pkgs.jdk21_headless}
			export ANDROID_HOME=$HOME/.local/share/android/sdk/
			'';
		};
  };
}
