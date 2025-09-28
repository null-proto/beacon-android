{
  description = "A very basic flake";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable";
  };

  outputs = { self, nixpkgs }:
	let
	system = "x86_64-linux";
	packages = nixpkgs.legacyPackages.${system};
	in{
		devShells.${system}.androidDev = packages.mkShell {
			buildInputs = [
				packages.android-studio
			];

			shellHook = ''
			  # none
			'';
		};
  };
}
