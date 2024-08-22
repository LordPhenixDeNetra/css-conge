import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { SalarierListComponent } from './salarier/salarier-list.component';
import { SalarierAddComponent } from './salarier/salarier-add.component';
import { SalarierEditComponent } from './salarier/salarier-edit.component';
import { SiteListComponent } from './site/site-list.component';
import { SiteAddComponent } from './site/site-add.component';
import { SiteEditComponent } from './site/site-edit.component';
import { DossierListComponent } from './dossier/dossier-list.component';
import { DossierAddComponent } from './dossier/dossier-add.component';
import { DossierEditComponent } from './dossier/dossier-edit.component';
import { DemandeCongeListComponent } from './demande-conge/demande-conge-list.component';
import { DemandeCongeAddComponent } from './demande-conge/demande-conge-add.component';
import { DemandeCongeEditComponent } from './demande-conge/demande-conge-edit.component';
import { ErrorComponent } from './error/error.component';
import {SalarierInfoComponent} from "./salarier/salarier-info/salarier-info.component";
import {DmtAddComponent} from "./dmt-add/dmt-add.component";
import {UsersAdminComponent} from "./users/users-admin/users-admin.component";


export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: $localize`:@@home.index.headline:Welcome to your new app!`
  },
  {
    path: 'salariers',
    component: SalarierListComponent,
    title: $localize`:@@salarier.list.headline:Salariers`
  },
  {
    path: 'salariers/add',
    component: SalarierAddComponent,
    title: $localize`:@@salarier.add.headline:Add Salarier`
  },
  {
    path: 'salariers/edit/:id',
    component: SalarierEditComponent,
    title: $localize`:@@salarier.edit.headline:Edit Salarier`
  },
  {
    path: 'sites',
    component: SiteListComponent,
    title: $localize`:@@site.list.headline:Sites`
  },
  {
    path: 'sites/add',
    component: SiteAddComponent,
    title: $localize`:@@site.add.headline:Add Site`
  },
  {
    path: 'sites/edit/:id',
    component: SiteEditComponent,
    title: $localize`:@@site.edit.headline:Edit Site`
  },
  {
    path: 'dossiers',
    component: DossierListComponent,
    title: $localize`:@@dossier.list.headline:Dossiers`
  },
  {
    path: 'dossiers/add/:id',
    component: DossierAddComponent,
    title: $localize`:@@dossier.add.headline:Add Dossier`
  },
  {
    path: 'dossiers/edit/:id',
    component: DossierEditComponent,
    title: $localize`:@@dossier.edit.headline:Edit Dossier`
  },
  {
    path: 'demandeConges',
    component: DemandeCongeListComponent,
    title: $localize`:@@demandeConge.list.headline:Demande Conges`
  },
  // {
  //   path: 'demandeConges/add',
  //   component: DemandeCongeAddComponent,
  //   title: $localize`:@@demandeConge.add.headline:Add Demande Conge`
  // },
  {
    path: 'demandeConges/add/:id',
    component: DemandeCongeAddComponent,
    title: $localize`:@@demandeConge.add.headline:Add Demande Conge`
  },
  {
    path: 'demandeConges/edit/:id',
    component: DemandeCongeEditComponent,
    title: $localize`:@@demandeConge.edit.headline:Edit Demande Conge`
  },

  {
    path: 'salarierInfo/info/:id',
    component: SalarierInfoComponent,
    title: $localize`:@@salarierInfo.info.headline:Congés Info`
  },

  {
    path: 'dmt',
    component: DmtAddComponent,
    title: $localize`:@@dmt.add.headline:Add DMT`
  },

  {
    path: 'users-admin',
    component: UsersAdminComponent,
    title: $localize`:@@dmt.add.headline:Admin`
  },

  {
    path: 'error',
    component: ErrorComponent,
    title: $localize`:@@error.headline:Error`
  },
  {
    path: '**',
    component: ErrorComponent,
    title: $localize`:@@notFound.headline:Page not found`
  }
];
