export class NMessageDTO{

  constructor(data:Partial<NMessageDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  message?: string|null;
  salarier?: number|null;
  user?: number|null;

}
