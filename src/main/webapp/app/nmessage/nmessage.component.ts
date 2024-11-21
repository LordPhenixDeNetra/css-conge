import {Component, inject, OnInit} from '@angular/core';
import {SidbarComponent} from "../common/sidbar/sidbar.component";
import {NgForOf, NgIf} from "@angular/common";
import {NMessageDTO} from "./nmessage.model";
import {NMessageService} from "./nmessage.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-nmessage',
  standalone: true,
  imports: [
    SidbarComponent,
    NgForOf,
    NgIf
  ],
  templateUrl: './nmessage.component.html',
  styleUrl: './nmessage.component.scss'
})
export class NMessageComponent implements OnInit{
  messages!:NMessageDTO[];
  nMessageService = inject(NMessageService);
  router = inject(Router)
  activatedRoute = inject(ActivatedRoute)

  ngOnInit() {
    const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    if (idParam) { // Vérifie si idParam n'est pas null
      const id = Number(idParam); // Convertit la chaîne en nombre
      if (!isNaN(id)) { // Vérifie si la conversion est réussie
        this.nMessageService.findAllMessageBySalarierId(id).subscribe({
          next : messages => {
            this.messages = messages;
          }
        });
      } else {
        console.error("Invalid 'id' parameter: not a number.");
      }
    } else {
      console.error("'id' parameter is null.");
    }
  }


}
