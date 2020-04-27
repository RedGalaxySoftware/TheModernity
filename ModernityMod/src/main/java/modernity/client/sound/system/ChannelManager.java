/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package modernity.client.sound.system;


// TODO Re-evaluate
//@OnlyIn( Dist.CLIENT )
//public class ChannelManager {
//    private final Set<Channel> channels = Sets.newIdentityHashSet();
//    private final SourceManager manager;
//    private final Executor executor;
//
//    public ChannelManager( SourceManager manager, Executor sndExecutor ) {
//        this.manager = manager;
//        this.executor = sndExecutor;
//    }
//
//    public Channel createChannel( SoundSystem.Mode mode ) {
//        Channel channel = new Channel();
//        executor.execute( () -> {
//            EFXSoundSource src = manager.getSoundSource( mode );
//            if( src != null ) {
//                channel.source = src;
//                channels.add( channel );
//            }
//
//        } );
//        return channel;
//    }
//
//    public void runOnSoundExecutor( Consumer<Stream<EFXSoundSource>> streamConsumer ) {
//        executor.execute(
//            () -> streamConsumer.accept(
//                channels.stream()
//                        .map( e -> e.source )
//                        .filter( Objects::nonNull )
//            )
//        );
//    }
//
//    public void tick() {
//        executor.execute( () -> {
//            Iterator<Channel> itr = channels.iterator();
//
//            while( itr.hasNext() ) {
//                Channel channel = itr.next();
//                channel.source.streamUpdate();
//                if( channel.source.isStopped() ) {
//                    channel.release();
//                    itr.remove();
//                }
//            }
//
//        } );
//    }
//
//    public void releaseAll() {
//        channels.forEach( Channel::release );
//        channels.clear();
//    }
//
//    @OnlyIn( Dist.CLIENT )
//    public class Channel {
//        private EFXSoundSource source;
//        private boolean released;
//
//        public boolean isReleased() {
//            return released;
//        }
//
//        public void runOnSoundExecutor( Consumer<EFXSoundSource> handler ) {
//            executor.execute( () -> {
//                if( source != null ) {
//                    handler.accept( source );
//                }
//
//            } );
//        }
//
//        public void release() {
//            released = true;
//            manager.release( source );
//            source = null;
//        }
//    }
//}