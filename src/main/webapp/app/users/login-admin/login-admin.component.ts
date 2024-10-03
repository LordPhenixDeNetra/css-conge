import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {SalarierService} from "../../salarier/salarier.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {HashService} from "../../services/hash.service";
import {UserService} from "../user.service";

@Component({
  selector: 'app-login-admin',
  standalone: true,
    imports: [
        FormsModule,
        ReactiveFormsModule
    ],
  templateUrl: './login-admin.component.html',
  styleUrl: './login-admin.component.scss'
})
export class LoginAdminComponent implements OnInit{

  constructor(private userService: UserService,
              private router : Router,
              private formBuilder : FormBuilder,
              private toast : ToastrService,
              private hashService : HashService
  ) {}

  ngOnInit() {

  }

  loginForm = this.formBuilder.group({
    email: this.formBuilder.control("", Validators.compose([Validators.required])),
    password: this.formBuilder.control("", Validators.compose([Validators.required])),
  });

  handleSubmit() {
    console.log(this.loginForm.value)

    if (this.loginForm.valid){
      let emailAdmin = this.loginForm.value.email!;
      let passwordAdmin = this.loginForm.value.password!;
      // let passwordAdmin = this.hashService.hashString(this.loginForm.value.password!);

      // let testPass = this.hashService.hashString("Passer123")
      // let testPass = "$2a$12$2U9/xgB9UsqKsjAGUOg5XONX7a8Gz40y527kxKhWhkZiY3rREsOrq"
      //
      // console.log("PWD : ",passwordAdmin)

      this.userService.loginAdmin(emailAdmin, passwordAdmin).subscribe(
        userResponse => {

          if(userResponse != null){
            sessionStorage.setItem('admin', JSON.stringify(userResponse));
            this.router.navigate(['/users-admin'])
          }else {
            this.toast.error("Erreur lors de la connexion\n" +
              "veuillez verifier vos entrées", "Erreur de connexion",{
              // timeOut: 10000,
              // progressBar:true,
            });
          }

        },
        (error) => {
          console.error("Erreur lors de la connexion: ", error);
          this.toast.error("Erreur lors de la connexion\n" +
            "veuillez verifier vos entrées", "Erreur de connexion",{
            // timeOut: 10000,
            // progressBar:true,
          });
          // this.router.navigate([`/dmt`]);
        }
      )
    }else {
      console.log("NOTE VALIDE")
      this.toast.error("Erreur lors de la connexion\n" +
        "veuillez verifier vos entrées", "Erreur de connexion",{
        // timeOut: 10000,
        // progressBar:true,
      });
    }
  }
}
