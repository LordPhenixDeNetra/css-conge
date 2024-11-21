import {Component, OnInit} from '@angular/core';
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";

interface Message {
  text: string;
  type: 'user' | 'bot';
}

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [
    NgClass,
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss',

  // template: `
  //   <div class="chat-container">
  //     <button class="chat-button" (click)="toggleChat()">💬</button>
  //
  //     <div *ngIf="isChatOpen" class="chat-window">
  //       <div class="chat-header">
  //         <span>Support IA</span>
  //         <button (click)="toggleChat()">✖️</button>
  //       </div>
  //
  //       <div class="chat-messages">
  //         <div *ngFor="let message of messages"
  //              [ngClass]="{
  //                'message': true,
  //                'user-message': message.type === 'user',
  //                'bot-message': message.type === 'bot'
  //              }">
  //           {{ message.text }}
  //         </div>
  //       </div>
  //
  //       <div class="message-input-container">
  //         <input
  //           type="text"
  //           [(ngModel)]="currentMessage"
  //           (keyup.enter)="sendMessage()"
  //           placeholder="Écrivez votre message..."
  //         >
  //         <button (click)="sendMessage()">▶️</button>
  //       </div>
  //     </div>
  //   </div>
  // `,

  // styles: [`
  //   .chat-container {
  //     position: fixed;
  //     bottom: 20px;
  //     right: 20px;
  //     z-index: 1000;
  //   }
  //   .chat-button {
  //     width: 60px;
  //     height: 60px;
  //     border-radius: 50%;
  //     background-color: #4CAF50;
  //     border: none;
  //     color: white;
  //     font-size: 24px;
  //     cursor: pointer;
  //   }
  //   .chat-window {
  //     width: 350px;
  //     height: 500px;
  //     background: white;
  //     border-radius: 10px;
  //     box-shadow: 0 0 10px rgba(0,0,0,0.1);
  //     display: flex;
  //     flex-direction: column;
  //   }
  //   .chat-messages {
  //     flex-grow: 1;
  //     overflow-y: auto;
  //     padding: 10px;
  //   }
  //   .message {
  //     margin: 10px 0;
  //     padding: 10px;
  //     border-radius: 10px;
  //     max-width: 80%;
  //   }
  //   .user-message {
  //     background-color: #DCF8C6;
  //     align-self: flex-end;
  //     margin-left: auto;
  //   }
  //   .bot-message {
  //     background-color: #F1F0F0;
  //     align-self: flex-start;
  //   }
  // `]

})
export class ChatComponent implements OnInit {
  isChatOpen = false;
  messages: Message[] = [];
  currentMessage = '';

  private botResponses: { [key: string]: string[] } = {
    'bonjour': [
      'Bonjour ! Comment puis-je vous aider aujourd\'hui ?',
      'Salut ! Je suis votre assistant virtuel. Que puis-je faire pour vous ?'
    ],
    'aide': [
      'Je suis là pour vous aider. Quel est votre problème ?',
      'Bien sûr, je serai ravi de vous assister. Pouvez-vous me donner plus de détails ?'
    ],
    'prix': [
      'Les prix varient selon les services. Voulez-vous que je vous donne plus d\'informations ?',
      'Nos tarifs sont compétitifs. Quel type de service vous intéresse ?'
    ],
    'défaut': [
      'Je vous écoute. Comment puis-je vous être utile ?',
      'Je suis là pour répondre à vos questions.',
      'Dites-m\'en plus, je suis à votre écoute.'
    ]
  };

  ngOnInit() {}

  toggleChat() {
    this.isChatOpen = !this.isChatOpen;
  }

  sendMessage() {
    if (this.currentMessage.trim()) {
      // Ajouter le message de l'utilisateur
      this.messages.push({
        text: this.currentMessage,
        type: 'user'
      });

      // Simuler une réponse du bot
      setTimeout(() => {
        const botResponse = this.getBotResponse(this.currentMessage);
        this.messages.push({
          text: botResponse,
          type: 'bot'
        });
      }, 1000);

      this.currentMessage = '';
    }
  }

  private getBotResponse(message: string): string {
    const lowerMessage = message.toLowerCase();
    for (const key in this.botResponses) {
      if (lowerMessage.includes(key)) {
        const responses = this.botResponses[key];
        return responses[Math.floor(Math.random() * responses.length)];
      }
    }
    const defaultResponses = this.botResponses['défaut'];
    return defaultResponses[Math.floor(Math.random() * defaultResponses.length)];
  }
}
