// import {
//   ActivatedRouteSnapshot,
//   CanActivate,
//   CanActivateFn,
//   Router,
//   RouterStateSnapshot,
//   UrlTree
// } from '@angular/router';
// import {Injectable} from "@angular/core";
// import {ToastrService} from "ngx-toastr";
// import {UserService} from "../users/user.service";
// import {Observable} from "rxjs";
//
// // export const isAdminGuardGuard: CanActivateFn = (route, state) => {
// //   return true;
// // };
//
//
//
// @Injectable({
//   providedIn: 'root'
// })
// export class IsAdminGuardGuard implements CanActivate {
//   constructor(
//     private toast: ToastrService,
//     private userService: UserService,
//     private router: Router
//   ) {}
//
//   canActivate(
//     route: ActivatedRouteSnapshot,
//     state: RouterStateSnapshot
//   ): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
//
//     if (this.userService.  getUserRole() === 'true') {
//       // Si l'utilisateur est connecté, renvoie `true`
//       return true; // Assure-toi que la méthode renvoie bien un boolean
//     } else {
//       // Si l'utilisateur n'est pas connecté, redirige vers la page de login
//       // this.router.navigate(['login']);
//       return false; // Renvoie `false` pour bloquer l'accès à la route
//     }
//   }
// }

